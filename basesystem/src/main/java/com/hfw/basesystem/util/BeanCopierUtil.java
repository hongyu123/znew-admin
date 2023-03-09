package com.hfw.basesystem.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * cglib BeanCopier 属性拷贝工具类
 * @author farkle
 * @date 2023-03-01
 */
public class BeanCopierUtil {

    //缓存
    public static Map<String, BeanCopier> beanCopierMap = new HashMap<>();

    /**
     * 属性浅拷贝
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copy(Object source, Object target) {
        String key = source.getClass().getName()+"-"+target.getClass().getName();
        BeanCopier copier = beanCopierMap.get(key);
        if(copier==null){
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(key,copier);
        }
        copier.copy(source, target, null);
    }

}
