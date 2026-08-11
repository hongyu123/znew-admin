package com.hfw.model.utils;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class LimitedParamMap implements Map<String, String> {
    private final LoadingCache<String, String> cache;
    public LimitedParamMap(int maxSize) {
        cache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .build(key -> {
                    return null; // 对于不存在的键，返回null或适当的默认值
                });
    }

    @Override
    public int size() {
        return (int) cache.estimatedSize();
    }

    @Override
    public boolean isEmpty() {
        return size()<=0;
    }

    @Override
    public boolean containsKey(Object key) {
        return cache.getIfPresent(key.toString())!=null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public String get(Object key) {
        return cache.getIfPresent(key.toString());
    }

    @Override
    public String put(String key, String value) {
        cache.put(key,value);
        return null;
    }

    @Override
    public String remove(Object key) {
        cache.invalidate(key.toString());
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        cache.putAll(m);
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }

    @Override
    public Set<String> keySet() {
        return cache.asMap().keySet();
    }

    @Override
    public Collection<String> values() {
        return cache.asMap().values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return cache.asMap().entrySet();
    }

}
