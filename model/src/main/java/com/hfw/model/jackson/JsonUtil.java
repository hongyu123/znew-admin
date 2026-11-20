package com.hfw.model.jackson;

import com.hfw.model.utils.LocalDateUtil;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.*;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.databind.ser.ValueSerializerModifier;
import tools.jackson.databind.ser.std.StdSerializer;
import tools.jackson.databind.ser.std.ToStringSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

public class JsonUtil {
    private volatile static JsonMapper jsonMapper;
    public static JsonMapper jsonMapper(){
        if(jsonMapper==null){
            synchronized (JsonUtil.class){
                if(jsonMapper==null){
                    jsonMapper = JsonUtil.builder().build();
                }
            }
        }
        return jsonMapper;
    }
    public static JsonMapper.Builder builder(){
        StdSerializer<Object> emptyArraySerializer = new StdSerializer<>(Object.class) {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
                gen.writeStartArray();
                gen.writeEndArray();
            }
        };
        return JsonMapper
                .builder()
                //.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // 未知属性报错
                //.changeDefaultPropertyInclusion(include -> include.withValueInclusion(JsonInclude.Include.NON_NULL) )  //不输出null值
                //.filterProvider(new SimpleFilterProvider().setFailOnUnknownId(false)) // @JsonFilter 不报错
                .defaultTimeZone(TimeZone.getTimeZone("Asia/Shanghai"))
                .defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .addModule(
                        new SimpleModule()
                                .addSerializer(Long.class, ToStringSerializer.instance)
                                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .addDeserializer(LocalDate.class, new ValueDeserializer<>() {
                                    @Override
                                    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws JacksonException {
                                        String text = jsonParser.getString().trim();
                                        return LocalDateUtil.parseDate(text);
                                    }
                                })
                                .addDeserializer(LocalDateTime.class, new ValueDeserializer<>() {
                                    @Override
                                    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws JacksonException {
                                        String text = jsonParser.getString().trim();
                                        return LocalDateUtil.parseDateTime(text);
                                    }
                                })
                                .setSerializerModifier(new ValueSerializerModifier(){
                                    @Override
                                    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription.Supplier beanDesc, List<BeanPropertyWriter> beanProperties) {
                                        for (BeanPropertyWriter writer : beanProperties) {
                                            JavaType type = writer.getType();
                                            if (type != null && (type.isArrayType() || type.isCollectionLikeType())) {
                                                // 数组,Collect类型null处理
                                                writer.assignNullSerializer(emptyArraySerializer);
                                            }
                                        }
                                        return beanProperties;
                                    }
                                })
                );
    }

}
