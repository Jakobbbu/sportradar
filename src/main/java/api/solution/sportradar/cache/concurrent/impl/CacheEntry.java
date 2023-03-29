package api.solution.sportradar.cache.concurrent.impl;

import api.solution.sportradar.cache.concurrent.ConcurrentCacheEntry;

public record CacheEntry<V>(V value) implements ConcurrentCacheEntry<V> {

}