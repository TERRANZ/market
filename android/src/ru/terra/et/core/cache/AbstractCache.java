package ru.terra.et.core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractCache<K, V> {
    protected HashMap<K, V> cacheMap = new HashMap<K, V>();

    public void addToCache(K key, V value) {
        cacheMap.put(key, value);
    }

    public V getFromCache(K key) {
        return cacheMap.get(key);
    }

    public List<V> getAll() {
        return new ArrayList<V>(cacheMap.values());
    }

    public void clear() {
        cacheMap.clear();
    }

    public Integer size() {
        return cacheMap.size();
    }
}
