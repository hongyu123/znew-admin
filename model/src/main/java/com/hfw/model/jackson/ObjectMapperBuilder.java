package com.hfw.model.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hfw.model.utils.LocalDateUtil;
import com.hfw.model.utils.StrUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class ObjectMapperBuilder{
    private volatile static ObjectMapper objectMapper;
    public static ObjectMapper buildSingleton(){
        if(objectMapper==null){
            synchronized (ObjectMapperBuilder.class){
                if(objectMapper==null){
                    objectMapper = build();
                    //针对使用了@JsonFilter注解的实体,序列化所有字段
                    FilterProvider filterProvider = new SimpleFilterProvider().addFilter(Result.INCLUDE_FILTER, SimpleBeanPropertyFilter.serializeAll());
                    objectMapper.setFilterProvider(filterProvider);
                }
            }
        }
        return objectMapper;
    }
    public static ObjectMapper build(){
        ObjectMapper objectMapper = new ObjectMapper();
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //允许序列化空对象(Object)
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //空字符串可以序列化成枚举,对象,Map,集合
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        //非法枚举序列化为null
        //objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        //自定义字段为null处理
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new NullSerializerModifier()));
        //空对象null处理
        //objectMapper.getSerializerProvider().setNullValueSerializer(new NullSerializer(NullType.NullObject));
        //设置时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        //设置日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        //自定义类型序列化和反序列化
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(Result.class, new ResultSerializer());
        //module.addSerializer(BigDecimal.class, new BigDecimalSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        module.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        module.addDeserializer(LocalDateTime.class, new JsonDeserializer<>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                String text = jsonParser.getText().trim();
                if(!StrUtil.hasText(text)){
                    return null;
                }
                /**
                 * 时间戳转日期
                try {
                    Long timestamp  = Long.valueOf(text);
                    return LocalDateUtil.toLocalDateTime(timestamp);
                }catch (NumberFormatException e){
                    return LocalDateUtil.parseDateTime(text);
                } */
                return LocalDateUtil.parseDateTime(text);
            }
        });
        module.addDeserializer(LocalDate.class, new JsonDeserializer<>() {
            @Override
            public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                String text = jsonParser.getText().trim();
                if(!StrUtil.hasText(text)){
                    return null;
                }
                return LocalDateUtil.parse(text);
            }
        });
        objectMapper.registerModule(module);
        return objectMapper;
    }

}
