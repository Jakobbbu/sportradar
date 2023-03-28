package api.solution.sportradar.model;

import java.time.ZonedDateTime;

public class Match {

    private final Long id;

    private final String sport;

    private final String tournament;

    private final ZonedDateTime startTime;

    private final MatchStatus matchStatus;

    private final String homeTeam;

    private final String awayTeam;

    private final short homeScore;

    private final short awayScore;

    private Match(Builder matchBuilder) {
        this.id = matchBuilder.id;
        this.sport = matchBuilder.sport;
        this.tournament = matchBuilder.tournament;
        this.startTime = matchBuilder.startTime;
        this.matchStatus = matchBuilder.matchStatus;
        this.homeTeam = matchBuilder.homeTeam;
        this.awayTeam = matchBuilder.awayTeam;
        this.homeScore = matchBuilder.homeScore;
        this.awayScore = matchBuilder.awayScore;
    }

    public Long getId() {
        return id;
    }

    public String getSport() {
        return sport;
    }

    public String getTournament() {
        return tournament;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public short getHomeScore() {
        return homeScore;
    }

    public short getAwayScore() {
        return awayScore;
    }

    public static class Builder {
        private Long id;

        private String sport;

        private String tournament;

        private ZonedDateTime startTime;

        private MatchStatus matchStatus;

        private String homeTeam;

        private String awayTeam;

        private short homeScore;

        private short awayScore;

        public Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withSport(String sport) {
            this.sport = sport;
            return this;
        }

        public Builder withTournament(String tournament) {
            this.tournament = tournament;
            return this;
        }

        public Builder withStartTime(ZonedDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder withMatchStatus(MatchStatus matchStatus) {
            this.matchStatus = matchStatus;
            return this;
        }

        public Builder withHomeTeam(String homeTeam) {
            this.homeTeam = homeTeam;
            return this;
        }

        public Builder withAwayTeam(String awayTeam) {
            this.awayTeam = awayTeam;
            return this;
        }

        public Builder withHomeScore(short homeScore) {
            this.homeScore = homeScore;
            return this;
        }

        public Builder withAwayScore(short awayScore) {
            this.awayScore = awayScore;
            return this;
        }

        public Match build() {
            return new Match(this);
        }
    }




}
