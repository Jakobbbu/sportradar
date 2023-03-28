package api.solution.sportradar.model.dto;

import api.solution.sportradar.model.MatchStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class MatchDTO {

    @JsonProperty
    private String sport;

    @JsonProperty
    private String tournament;

    @JsonProperty("start_time")
    private ZonedDateTime startTime;

    @JsonProperty("match_status")
    private MatchStatus matchStatus;

    @JsonProperty("home_team")
    private String homeTeam;

    @JsonProperty("away_team")
    private String awayTeam;

    @JsonProperty("home_score")
    private short homeScore;

    @JsonProperty("away_score")
    private short awayScore;


    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setHomeScore(short homeScore) {
        this.homeScore = homeScore;
    }

    public void setAwayScore(short awayScore) {
        this.awayScore = awayScore;
    }
}
