package com.hfw.admin.util;

import com.hfw.model.entity.SysDemo;

import java.util.HashMap;
import java.util.Map;

/**
 * 信息映射
 * @author farkle
 * @date 2023-03-17
 */
public class InfoMap {
    /**
     * 类映射
     */
    private static Map<String,Class> classMap = new HashMap<>();

    static {
        registClassMap(SysDemo.class);
    }

    public static void registClassMap(String key, Class clazz){
        classMap.putIfAbsent(key, clazz);
    }
    public static void registClassMap(Class clazz){
        classMap.putIfAbsent(clazz.getSimpleName(), clazz);
    }

    public static Class getClassMap(String key){
        return classMap.get(key);
    }

}
