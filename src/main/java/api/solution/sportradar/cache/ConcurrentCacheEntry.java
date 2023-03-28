package api.solution.sportradar.cache;

public interface ConcurrentCacheEntry<V> {

    V getValue();

    boolean isExpired();

}
