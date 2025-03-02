package com.hfw.model.entity;

/**
 * @author farkle
 * @date 2022-06-15
 */
public class Entry<K,V> {
    private final K k;
    private final V v;
    public Entry(K k, V v){
        this.k = k;
        this.v = v;
    }
    public K key(){
        return this.k;
    }
    public V value(){
        return this.v;
    }

}
