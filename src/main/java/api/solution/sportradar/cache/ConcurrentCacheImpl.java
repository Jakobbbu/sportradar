package api.solution.sportradar.cache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ConcurrentCacheImpl<K, V> implements ConcurrentCache<K,V> {

    private final ConcurrentHashMap<K, ConcurrentCacheEntry<V>> cache;

    public ConcurrentCacheImpl() {
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public void put(K key, V value, long expiration, TimeUnit unit) {
        CacheEntry<V> entry = new CacheEntry<>(value, expiration, unit);
        cache.put(key, entry);
    }

    @Override
    public V getValueForKey(K key) {
         ConcurrentCacheEntry<V> entry = cache.get(key);
         return entry.getValue();
    }

    @Override
    public ConcurrentCacheEntry<V> getEntryForKey(K key) {
        return cache.get(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public long size() {
        return cache.size();
    }

    @Override
    public Collection<ConcurrentCacheEntry<V>> getValues() {
        return cache.values();
    }

    public void remove(K key) {
        cache.remove(key);
    }

    private static class CacheEntry<V> implements ConcurrentCacheEntry<V> {
        private final V value;
        private final long expirationTime;

        public CacheEntry(V value, long expiration, TimeUnit unit) {
            this.value = value;
            this.expirationTime = System.currentTimeMillis() + unit.toMillis(expiration);
        }

        public V getValue() {
            return value;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }
}
