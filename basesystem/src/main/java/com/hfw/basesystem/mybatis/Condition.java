package com.hfw.basesystem.mybatis;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件
 * @author zyh
 * @date 2022-06-10
 */
public class Condition<T> {

    private List<Entry> condList = new ArrayList<>();
    private Class<T> clazz;
    private Condition() {}

    public Condition(Class<T> clazz){
        this.clazz = clazz;
    }

    public static <T> Condition<T> create(Class<T> clazz) {
        Condition condition = new Condition();
        condition.clazz = clazz;
        return condition;
    }

    public Condition<T> put(GetFunction<T,Object> fn, Object value){
        condList.add(new Entry(fn,value));
        return this;
    }

    public Class<T> getClazz() throws Exception {
        return clazz;
        /*if(condList==null || condList.size()<=0){
            return null;
        }
        return condList.get(0).fn.getClazz();*/
    }

    public List<Entry> getCondList(){
        return this.condList;
    }


    public class Entry{
        private GetFunction<T,Object> fn;
        private Object value;
        public Entry(GetFunction fn, Object value){
            this.fn = fn;
            this.value = value;
        }
        public GetFunction<T,Object> getFn(){
            return this.fn;
        }
        public Object getValue(){
            return this.value;
        }
    }

}
