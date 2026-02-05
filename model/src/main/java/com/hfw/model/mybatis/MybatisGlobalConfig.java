package com.hfw.model.mybatis;

import java.util.HashMap;
import java.util.Map;

public class MybatisGlobalConfig {
    public interface GetFunc {
        Object get(Class<?> type);
    }

    private final static Map<String,GetFunc> DYNAMIC_MAP = new HashMap<>();

    public static void registerDynamic(String expression, GetFunc dynamicFunc){
        DYNAMIC_MAP.put(expression, dynamicFunc);
    }

    public static Object getDynamic(String expression, Class<?> type){
        /*if(!expression.startsWith("$")){
            return expression;
        }*/
        return DYNAMIC_MAP.get(expression).get(type);
    }

    private static SqlHelper sqlHelper = new SqlHelper();

    public static SqlHelper getSqlHelper(){
        return sqlHelper;
    }

}
