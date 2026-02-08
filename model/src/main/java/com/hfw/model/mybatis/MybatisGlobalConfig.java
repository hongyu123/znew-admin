package com.hfw.model.mybatis;

import com.hfw.model.mybatis.override.DynamicConfiguration;
import com.hfw.model.utils.StrUtil;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.ehcache.impl.internal.concurrent.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static final Configuration configuration = new DynamicConfiguration();
    public static Configuration getConfiguration(){
        return configuration;
    }

    private final static Map<Class<?>, ResultMap> DYNAMIC_RESULT_MAP = new ConcurrentHashMap<>();
    public static void registerDynamicResultMap(Class<?> type, List<ColumnInfo> mapColumns){
        if(mapColumns==null || mapColumns.isEmpty()){
            return;
        }
        MapperBuilderAssistant assistant = new MapperBuilderAssistant(configuration, "");
        String id = SingleMapper.class.getName()+".select-dynamic";
        List<ResultMapping> resultMappings = new ArrayList<>();
        for(ColumnInfo column : mapColumns){
            if(!column.getColumnName().equals(StrUtil.humpToLine(column.getFieldName()))){
                ResultMapping resultMapping = assistant.buildResultMapping(type, column.getFieldName(), column.getColumnName(), null, null, null, null, null, null, null, null);
                resultMappings.add(resultMapping);
            }
        }
        ResultMap resultMap = (new ResultMap.Builder(configuration, id, type, resultMappings, null)).discriminator(null).build();
        DYNAMIC_RESULT_MAP.put(type, resultMap);
    }
    public static ResultMap getDynamicResultMap(Class<?> type){
        return DYNAMIC_RESULT_MAP.get(type);
    }

}
