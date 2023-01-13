package com.hfw.basesystem.mybatis;

/**
 * @author farkle
 * @date 2022-06-10
 */
@FunctionalInterface
public interface SetFunction<T,V> {
    void apply(T obj, V value);
}