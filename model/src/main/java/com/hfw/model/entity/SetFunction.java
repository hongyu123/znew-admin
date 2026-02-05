package com.hfw.model.entity;

@FunctionalInterface
public interface SetFunction<T,V> {
    void apply(T obj, V value);
}