package com.hfw.model.utils;

import java.util.HashMap;

/**
 * 提供链式put的Map工具类
 * @author farkle
 * @date 2022-04-06
 */
public class ChainMap extends HashMap<String, Object> {
    private ChainMap() {}

    public static ChainMap create() {
        return new ChainMap();
    }

    @Override
    public ChainMap put(String name, Object value) {
        super.put(name, value);
        return this;
    }

}