package api.solution.sportradar.data;

import api.solution.sportradar.cache.MatchCacheProvider;
import api.solution.sportradar.cache.concurrent.ConcurrentCacheEntry;
import api.solution.sportradar.model.Match;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataProvider {

    private final MatchCacheProvider matchCacheProvider;

    public DataProvider(MatchCacheProvider matchCacheProvider) {
        this.matchCacheProvider = matchCacheProvider;
    }

    public List<Match> getMatches() {
        List<Match> matchList = new ArrayList<>();
        for(ConcurrentCacheEntry<Match> matchEntry : matchCacheProvider.getAllEntries()) {
            matchList.add(matchEntry.value());
        }
        return matchList;
    }
}
