package com.hfw.model.utils;

import java.util.HashMap;

/**
 * 提供链式put的Map工具类
 * @author farkle
 * @date 2022-04-06
 */
public class ChainMap<T> extends HashMap<String, T> {
    private ChainMap() {}

    public static <T> ChainMap<T> create() {
        return new ChainMap<>();
    }

    public ChainMap<T> putVal(String key, T value) {
        super.put(key, value);
        return this;
    }

}