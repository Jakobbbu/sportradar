package api.solution.sportradar.cache;

import api.solution.sportradar.cache.concurrent.ConcurrentCacheEntry;
import api.solution.sportradar.model.Match;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MatchCacheProvider {

    public List<ConcurrentCacheEntry<Match>> getAllEntries() {
        return new ArrayList<>((MatchCache.INSTANCE.cache().getValues()));
    }

    public void cachePut(Match match) {
        MatchCache.INSTANCE.cache().put(match.getId(), match);
    }

}
