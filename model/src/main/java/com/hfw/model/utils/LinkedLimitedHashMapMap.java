package com.hfw.model.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedLimitedHashMapMap<K,V> extends LinkedHashMap<K,V> {
    private final int limit;

    public LinkedLimitedHashMapMap(int limit) {
        this.limit = limit;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() > limit;
    }

}
