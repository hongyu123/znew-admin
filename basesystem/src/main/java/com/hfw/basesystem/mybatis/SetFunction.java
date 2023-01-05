package com.hfw.basesystem.mybatis;

/**
 * @author zyh
 * @date 2022-06-10
 */
@FunctionalInterface
public interface SetFunction<T,V> {
    void apply(T obj, V value);
}