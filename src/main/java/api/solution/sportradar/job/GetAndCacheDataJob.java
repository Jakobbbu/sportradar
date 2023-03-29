package api.solution.sportradar.job;

import api.solution.sportradar.cache.MatchCacheProvider;
import api.solution.sportradar.client.WebClient;
import api.solution.sportradar.model.Match;
import api.solution.sportradar.model.MatchStatus;
import jakarta.annotation.PostConstruct;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetAndCacheDataJob {

    private static final Logger log = LoggerFactory.getLogger(GetAndCacheDataJob.class);

    private static final String ID = "id";
    private static final String TOURNAMENT_ID = "tournamentId";
    private static final String SPORT_ID = "sportId";
    private static final String START_TIME = "start_time";
    private static final String STATUS = "status";
    private static final String HOME_TEAM = "home_team";
    private static final String AWAY_TEAM = "away_team";
    private static final String HOME_SCORE = "home_score";
    private static final String AWAY_SCORE = "away_score";
    private static final String NAME = "name";
    private static final String ZERO = "0";

    private final WebClient webClient;

    private final MatchCacheProvider matchCacheProvider;

    public GetAndCacheDataJob(WebClient webClient, MatchCacheProvider matchCacheProvider) {
        this.webClient = webClient;
        this.matchCacheProvider = matchCacheProvider;
    }

    @PostConstruct
    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    @Async
    public void run() {
        getAndCacheData();
    }

    private void getAndCacheData() {
        log.info("Getting data");
        try {
            Map<Integer, JsonObject> sportsMap = getSportsMap();
            Map<Integer, JsonObject> tournamentsMap = getTournamentsMap();

            JsonArray results = webClient.getAllMatchesJson();
            for(JsonObject matchJson : results.getValuesAs(JsonObject.class)) {

                JsonObject tournamentJsonForMatch = tournamentsMap.get(matchJson.getInt(TOURNAMENT_ID));
                JsonObject sportJsonForTournament = sportsMap.get(tournamentJsonForMatch.getInt(SPORT_ID));

                Match match = buildMatchFromJson(matchJson, tournamentJsonForMatch, sportJsonForTournament);

                matchCacheProvider.cachePut(match);

                log.info("Updated cache for id: " + match.getId());
            }

        } catch (Exception e) {
            log.error("Could not update cache data:" +  e.getLocalizedMessage());
        }
    }

    private Match buildMatchFromJson(JsonObject matchJson, JsonObject tournamentJsonForMatch, JsonObject sportJsonForTournament) throws IOException {

        MatchStatus matchStatus = getMatchStatusForString(matchJson.getString(STATUS));

        return new Match.Builder()
                .withId((long) matchJson.getInt(ID))
                .withSport(sportJsonForTournament.getString(NAME))
                .withTournament(tournamentJsonForMatch.getString(NAME))
                .withStartTime(ZonedDateTime.parse(matchJson.getString(START_TIME)))
                .withMatchStatus(matchStatus)
                .withHomeTeam(matchJson.getString(HOME_TEAM))
                .withAwayTeam(matchJson.getString(AWAY_TEAM))
                .withHomeScore(Short.parseShort(matchStatus.equals(MatchStatus.SCHEDULED) ? ZERO : matchJson.getString(HOME_SCORE)))
                .withAwayScore(Short.parseShort(matchStatus.equals(MatchStatus.SCHEDULED) ? ZERO : matchJson.getString(AWAY_SCORE)))
                .build();
    }

    private MatchStatus getMatchStatusForString(String name) throws IOException {
        switch (name) {
            case "COMPLETED" -> {
                return MatchStatus.COMPLETED;
            }
            case "SCHEDULED" -> {
                return MatchStatus.SCHEDULED;
            }
            case "Live" -> {
                return MatchStatus.LIVE;
            }
            default -> throw new IOException();
        }
    }

    private Map<Integer, JsonObject> getTournamentsMap() throws IOException {

        Map<Integer, JsonObject> tournamentsMap = new HashMap<>();

        JsonArray results = webClient.getAllTournamentsJson();
        for (JsonObject tournamentJson : results.getValuesAs(JsonObject.class)) {
            tournamentsMap.put(tournamentJson.getInt(ID), tournamentJson);
        }

        return tournamentsMap;
    }

    private Map<Integer, JsonObject> getSportsMap() throws IOException {

        Map<Integer, JsonObject> sportsMap = new HashMap<>();

        JsonArray results = webClient.getAllSportsJson();
        for(JsonObject sportJson : results.getValuesAs(JsonObject.class)) {
            sportsMap.put(sportJson.getInt(ID), sportJson);
        }

        return sportsMap;
    }
}
