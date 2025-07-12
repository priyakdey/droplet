package com.priyakdey.droplet.cache;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Priyak Dey
 */
public class Cache<K, V> {

    private final ConcurrentMap<K, Entry<V>> table;
    private final long ttlInMillis;

    public Cache(long ttl, TimeUnit timeUnit) {
        this.table = new ConcurrentHashMap<>();
        this.ttlInMillis = timeUnit.toMillis(ttl);
    }

    public void put(K key, V value) {
        Instant expiresAt = Instant.now().plusMillis(ttlInMillis);
        Entry<V> entry = new Entry<>(value, expiresAt);
        table.put(key, entry);
    }

    public V get(K key) {
        Entry<V> entry = table.get(key);
        if (entry == null || entry.isExpired()) {
            table.remove(key);
            return null;
        }

        return entry.value;
    }

    public void evict(K key) {
        table.remove(key);
    }

    private record Entry<V>(V value, Instant expiresAt) {

        boolean isExpired() {
                return Instant.now().isAfter(expiresAt);
            }
        }

}
