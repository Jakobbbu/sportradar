package api.solution.sportradar.cache;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public interface ConcurrentCache<K, V> {

    void put(K key, V value, long expiration, TimeUnit unit);

    void remove(K key);

    V getValueForKey(K key);

    ConcurrentCacheEntry<V> getEntryForKey(K key);
    void clear();

    long size();

    Collection<ConcurrentCacheEntry<V>> getValues();
}
