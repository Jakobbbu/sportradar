package api.solution.sportradar.cache.concurrent.impl;

import api.solution.sportradar.cache.concurrent.ConcurrentCache;
import api.solution.sportradar.cache.concurrent.ConcurrentCacheEntry;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCacheImpl<K, V> implements ConcurrentCache<K,V> {

    private final ConcurrentHashMap<K, ConcurrentCacheEntry<V>> cache;

    public ConcurrentCacheImpl() {
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public void put(K key, V value) {
        CacheEntry<V> entry = new CacheEntry<>(value);
        cache.put(key, entry);
    }

    @Override
    public V getValueForKey(K key) {
         ConcurrentCacheEntry<V> entry = cache.get(key);
         return entry.value();
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

}
