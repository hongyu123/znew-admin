package com.hfw.common.support;

import java.util.HashMap;

/**
 * 提供链式put的Map工具类
 * @author farkle
 * @date 2022-04-06
 */
public class ParamMap  extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private ParamMap() {}

    public static ParamMap create() {
        return new ParamMap();
    }

    @Override
    public ParamMap put(String name, Object value) {
        super.put(name, value);
        return this;
    }

}