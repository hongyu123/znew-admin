package com.hfw.model.mybatis;

import com.hfw.model.mybatis.override.DynamicConfiguration;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private static final Configuration configuration = new DynamicConfiguration();
    public static Configuration getConfiguration(){
        return configuration;
    }

    private final static Map<Class<?>, ResultMap> DYNAMIC_RESULT_MAP = new ConcurrentHashMap<>();
    // 存储不映射ResultMap的类型, Map.class,HashMap.class
    private final static Map<Class<?>, String> UN_RESULT_MAP = new ConcurrentHashMap<>();
    public static ResultMap registerDynamicResultMap(Class<?> type, List<ColumnInfo> mapColumns){
        // 为空时为不映射ResultMap的类型, 如 Map.class,HashMap.class
        if(mapColumns==null){
            UN_RESULT_MAP.put(type, "");
            return null;
        }
        //MapperBuilderAssistant assistant = new MapperBuilderAssistant(configuration, "");
        String id = SingleMapper.class.getName()+".select-dynamic";
        List<ResultMapping> resultMappings = new ArrayList<>();
        for(ColumnInfo column : mapColumns){
            //ResultMapping resultMapping = assistant.buildResultMapping(type, column.getFieldName(), column.getColumnName(), null, null, null, null, null, null, null, null);
            ResultMapping resultMapping = column.buildResultMapping(configuration);
            resultMappings.add(resultMapping);
        }
        ResultMap resultMap = (new ResultMap.Builder(configuration, id, type, resultMappings, null)).discriminator(null).build();
        DYNAMIC_RESULT_MAP.put(type, resultMap);
        System.out.println(DYNAMIC_RESULT_MAP);
        return resultMap;
    }
    public static ResultMap getDynamicResultMap(Class<?> type){
        ResultMap resultMap = DYNAMIC_RESULT_MAP.get(type);
        if(resultMap!=null){
            return resultMap;
        }
        if(UN_RESULT_MAP.containsKey(type)){
            return null;
        }
        // 返回类型为实体类的自定义sql 在前执行时,有机会注册ResultMap
        return sqlHelper.resultMap(type);
    }

}
