package api.solution.sportradar.job;

import api.solution.sportradar.cache.MatchCacheProvider;
import api.solution.sportradar.client.WebClient;
import api.solution.sportradar.model.Match;
import api.solution.sportradar.model.MatchStatus;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetAndCacheDataJobTest {

    @Mock
    private WebClient webClient;

    @Mock
    private MatchCacheProvider matchCacheProvider;

    private GetAndCacheDataJob getAndCacheDataJob;

    private static final String sportsJson = "[{\"id\":1,\"name\":\"Football\"},{\"id\":2,\"name\":\"Basketball\"},{\"id\":3,\"name\":\"IceHockey\"},{\"id\":4,\"name\":\"Baseball\"},{\"id\":5,\"name\":\"Americanfootball\"}]";

    private static final  String tournamentsJson = "[{\"id\":1,\"sportId\":1,\"name\":\"UEFAChampionsleague\"},{\"id\":2,\"sportId\":2,\"name\":\"NBA\"},{\"id\":3,\"sportId\":2,\"name\":\"Eurobasket\"},"
            + "{\"id\":4,\"sportId\":1,\"name\":\"UEFAEuropeleague\"},{\"id\":5,\"sportId\":3,\"name\":\"NHL\"},{\"id\":6,\"sportId\":4,\"name\":\"MLB\"},{\"id\":7,\"sportId\":5,\"name\":\"NFL\"}]";

    private static final String matchJson = "[{\"id\":1,\"tournamentId\":2,\"start_time\":\"2022-02-06T03:10:38Z\",\"status\":\"COMPLETED\",\"home_team\":\"Sacramento Kings\",\"away_team\":\"Oklahoma CityThunder\"," +
            "\"home_score\":\"113\",\"away_score\":\"103\"},{\"id\":2,\"tournamentId\":2,\"start_time\":\"2022-02-06T03:11:26Z\",\"status\":\"COMPLETED\",\"home_team\":\"Portland Trail Blazers\"," +
            "\"away_team\":\"Milwaukee Bucks\",\"home_score\":\"108\",\"away_score\":\"137\"},{\"id\":3,\"tournamentId\":2,\"start_time\":\"2023-04-06T20:41:04Z\",\"status\":\"SCHEDULED\"," +
            "\"home_team\":\"Denver Nuggets\",\"away_team\":\"Brooklyn Nets\"},{\"id\":4,\"tournamentId\":5,\"start_time\":\"2023-02-06T20:41:47Z\",\"status\":\"Live\",\"home_team\":\"Detroit Redwings\"," +
            "\"away_team\":\"LosAngeles Kings\",\"home_score\":\"1\",\"away_score\":\"1\"},{\"id\":5,\"tournamentId\":5,\"start_time\":\"2022-02-06T20:42:13Z\",\"status\":\"COMPLETED\",\"home_team\":\"Chicago Blackhawks\"," +
            "\"away_team\":\"Philadelphia Flyers\",\"home_score\":\"3\",\"away_score\":\"5\"},{\"id\":6,\"tournamentId\":6,\"start_time\":\"2022-02-08T20:42:13Z\",\"status\":\"COMPLETED\",\"home_team\":\"Los Angeles Kings\"," +
            "\"away_team\":\"Chicago Blackhawks\",\"home_score\":\"2\",\"away_score\":\"3\"}]";

    private final List<Match> entries = new ArrayList<>();

    @BeforeEach
    public void setUp() throws IOException {

        JsonArray matches = Json.createReader(new StringReader(matchJson)).readArray();
        JsonArray tournaments = Json.createReader(new StringReader(tournamentsJson)).readArray();
        JsonArray sports = Json.createReader(new StringReader(sportsJson)).readArray();

        when(webClient.getAllMatchesJson()).thenReturn(matches);
        when(webClient.getAllSportsJson()).thenReturn(sports);
        when(webClient.getAllTournamentsJson()).thenReturn(tournaments);

        doAnswer(answer -> entries.add(answer.getArgument(0))).when(matchCacheProvider).cachePut(any(Match.class));

        this.getAndCacheDataJob = new GetAndCacheDataJob(webClient, matchCacheProvider);

    }

    @Test
    public void testConfig() {

        getAndCacheDataJob.run();

        assertEquals(entries.size(), 6);
        assertEquals(entries.get(0).getId(), 1);
        assertEquals(entries.get(1).getId(), 2);
        assertEquals(entries.get(2).getId(), 3);
        assertEquals(entries.get(3).getId(), 4);
        assertEquals(entries.get(4).getId(), 5);
        assertEquals(entries.get(5).getId(), 6);

        assertEquals(entries.get(0).getSport(), "Basketball");
        assertEquals(entries.get(1).getSport(), "Basketball");
        assertEquals(entries.get(2).getSport(), "Basketball");
        assertEquals(entries.get(3).getSport(), "IceHockey");
        assertEquals(entries.get(4).getSport(), "IceHockey");
        assertEquals(entries.get(5).getSport(), "Baseball");

        assertEquals(entries.get(0).getTournament(), "NBA");
        assertEquals(entries.get(1).getTournament(), "NBA");
        assertEquals(entries.get(2).getTournament(), "NBA");
        assertEquals(entries.get(3).getTournament(), "NHL");
        assertEquals(entries.get(4).getTournament(), "NHL");
        assertEquals(entries.get(5).getTournament(), "MLB");


        assertEquals(entries.get(0).getMatchStatus(), MatchStatus.COMPLETED);
        assertEquals(entries.get(1).getMatchStatus(), MatchStatus.COMPLETED);
        assertEquals(entries.get(2).getMatchStatus(), MatchStatus.SCHEDULED);
        assertEquals(entries.get(3).getMatchStatus(), MatchStatus.LIVE);
        assertEquals(entries.get(4).getMatchStatus(), MatchStatus.COMPLETED);
        assertEquals(entries.get(5).getMatchStatus(), MatchStatus.COMPLETED);

        assertEquals(entries.get(0).getHomeTeam(), "Sacramento Kings");
        assertEquals(entries.get(1).getHomeTeam(), "Portland Trail Blazers");
        assertEquals(entries.get(2).getHomeTeam(), "Denver Nuggets");
        assertEquals(entries.get(3).getHomeTeam(), "Detroit Redwings");
        assertEquals(entries.get(4).getHomeTeam(), "Chicago Blackhawks");
        assertEquals(entries.get(5).getHomeTeam(), "Los Angeles Kings");

    }

}
