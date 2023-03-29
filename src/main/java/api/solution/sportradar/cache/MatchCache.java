package api.solution.sportradar.cache;

import api.solution.sportradar.cache.concurrent.ConcurrentCache;
import api.solution.sportradar.cache.concurrent.impl.ConcurrentCacheImpl;
import api.solution.sportradar.model.Match;

public enum MatchCache {
    INSTANCE;
    private final transient ConcurrentCache<Long, Match> concurrentCache = new ConcurrentCacheImpl<>();

    public ConcurrentCache<Long, Match> cache() {
        return concurrentCache;
    }
}
