package api.solution.sportradar.service;

import api.solution.sportradar.cache.ConcurrentCacheEntry;
import api.solution.sportradar.cache.MatchCache;
import api.solution.sportradar.model.Match;
import api.solution.sportradar.model.MatchStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService{
    @Override
    public List<Match> getAll() {

        List<Match> allMatches = new ArrayList<>();

        for(ConcurrentCacheEntry<Match> entry : MatchCache.INSTANCE.cache().getValues()) {
            allMatches.add(entry.getValue());
        }

        return allMatches;

    }

    @Override
    public List<Match> getCompleted() {

        List<Match> completedMatches = new ArrayList<>();

        Match match;

        for(ConcurrentCacheEntry<Match> entry : MatchCache.INSTANCE.cache().getValues()) {
            match = entry.getValue();
            if(match.getMatchStatus().equals(MatchStatus.COMPLETED)) {
                completedMatches.add(match);
            }
        }

        return completedMatches;
    }

    @Override
    public List<Match> getLive() {

        List<Match> liveMatches = new ArrayList<>();

        Match match;

        for(ConcurrentCacheEntry<Match> entry : MatchCache.INSTANCE.cache().getValues()) {
            match = entry.getValue();
            if(match.getMatchStatus().equals(MatchStatus.LIVE)) {
                liveMatches.add(match);
            }
        }

        return liveMatches;
    }

    @Override
    public List<Match> getForTeam(String teamName) {

        List<Match> matchesForTeam = new ArrayList<>();

        Match match;

        for(ConcurrentCacheEntry<Match> entry : MatchCache.INSTANCE.cache().getValues()) {
            match = entry.getValue();
            if(match.getAwayTeam().matches(String.format("(.*)%s(.*)", teamName)) || match.getHomeTeam().matches(String.format("(.*)%s(.*)", teamName)) ) {
                matchesForTeam.add(match);
            }
        }

        return matchesForTeam;
    }
}
