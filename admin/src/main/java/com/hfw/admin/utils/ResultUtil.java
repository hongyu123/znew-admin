package com.hfw.admin.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.hfw.model.jackson.ObjectMapperBuilder;
import com.hfw.model.jackson.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * 指定属性json序列化工具
 */
public class ResultUtil {

    private static String json(Object data, String filterName, String... fields) throws JsonProcessingException {
        ObjectMapper objectMapper = ObjectMapperBuilder.build();
        //2.1创建过滤器
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        //2.1.1Person类的属性过滤器（只序列化car,house,name字段）
        filterProvider.addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept(fields));
        objectMapper.setFilterProvider(filterProvider);
        return objectMapper.writeValueAsString(data);
    }
    public static String jsonList(List<?> list, String... fields) throws JsonProcessingException {
        if(list == null){
            list = new ArrayList<>();
        }
        return json(Result.list(list), Result.INCLUDE_FILTER, fields);
    }
    public static String jsonData(Object data, String... fields) throws JsonProcessingException {
        return json(Result.success(data), Result.INCLUDE_FILTER, fields);
    }

}
