package com.hfw.model.entity;

public class ChainPO<T>{

    public static <T> ChainPO<T> init(T t){
        ChainPO<T> where = new ChainPO<>();
        where.t = t;
        return where;
    }

    private T t;

    public <V> ChainPO<T> set(SetFunction<T,V> set, V value){
        set.apply(t,value);
        return this;
    }
    public T get(){
        return this.t;
    }

}
