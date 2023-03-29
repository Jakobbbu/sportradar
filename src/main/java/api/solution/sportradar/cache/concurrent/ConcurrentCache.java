package api.solution.sportradar.cache.concurrent;

import java.util.Collection;

public interface ConcurrentCache<K, V> {

    void put(K key, V value);

    void remove(K key);

    V getValueForKey(K key);

    ConcurrentCacheEntry<V> getEntryForKey(K key);
    void clear();

    long size();

    Collection<ConcurrentCacheEntry<V>> getValues();
}
