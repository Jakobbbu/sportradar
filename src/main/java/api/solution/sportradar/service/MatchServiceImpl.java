package api.solution.sportradar.service;

import api.solution.sportradar.data.DataProvider;
import api.solution.sportradar.model.Match;
import api.solution.sportradar.model.MatchStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService{

    private final DataProvider dataProvider;

    public MatchServiceImpl(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Override
    public List<Match> getAll() {

        return new ArrayList<>(dataProvider.getMatches());

    }

    @Override
    public List<Match> getCompleted() {

        List<Match> completedMatches = new ArrayList<>();

        for(Match match : dataProvider.getMatches()) {
            if(match.getMatchStatus().equals(MatchStatus.COMPLETED)) {
                completedMatches.add(match);
            }
        }

        return completedMatches;
    }

    @Override
    public List<Match> getLive() {

        List<Match> liveMatches = new ArrayList<>();

        for(Match match : dataProvider.getMatches()) {
            if(match.getMatchStatus().equals(MatchStatus.LIVE)) {
                liveMatches.add(match);
            }
        }

        return liveMatches;
    }

    @Override
    public List<Match> getForTeam(String teamName) {

        List<Match> matchesForTeam = new ArrayList<>();

        String pattern = "(?i).*%s.*";

        for(Match match : dataProvider.getMatches()) {
            if(match.getAwayTeam().matches(String.format(pattern, teamName))
                    || match.getHomeTeam().matches(String.format(pattern, teamName)) ) {
                matchesForTeam.add(match);
            }
        }

        return matchesForTeam;
    }
}
