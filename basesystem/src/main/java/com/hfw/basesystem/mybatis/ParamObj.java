package com.hfw.basesystem.mybatis;

/**
 * 提供对象set方法链式调用的对象工具类
 * @author farkle
 * @date 2022-06-10
 */
public class ParamObj<T> {
    private T proxy;

    private ParamObj() {}

    public static <T> ParamObj<T> create(T proxy){
        ParamObj<T> paramObj = new ParamObj<>();
        paramObj.proxy = proxy;
        return paramObj;
    }
    public static <T> ParamObj<T> create(Class<T> clazz){
        ParamObj<T> paramObj = new ParamObj<>();
        try{
            paramObj.proxy = clazz.getDeclaredConstructor().newInstance();
        }catch (Exception e){
        }
        return paramObj;
    }

    public <V> ParamObj<T> set(SetFunction<T,V> fn, V value){
        fn.apply(this.proxy, value);
        return this;
    }
    public T get(){
        return this.proxy;
    }

}
