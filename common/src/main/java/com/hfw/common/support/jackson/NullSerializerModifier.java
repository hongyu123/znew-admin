package com.hfw.common.support.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * jackson自定义null处理
 * @author farkle
 * @date 2022-04-15
 */
public class NullSerializerModifier extends BeanSerializerModifier {

    private JsonSerializer<Object> nullArray = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeEndArray();
        }
    };
    private JsonSerializer<Object> nullString = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString("");
        }
    };
    private JsonSerializer<Object> nullNumber = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(0);
        }
    };
    private JsonSerializer<Object> nullBoolean = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeBoolean(false);
        }
    };
    private JsonSerializer<Object> nullDate = new JsonSerializer<Object>() {
        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString("");
        }
    };

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {

        //循环所有的beanPropertyWriter
        for (BeanPropertyWriter writer : beanProperties) {
            Class<?> clazz = writer.getType().getRawClass();
            //数组
            if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)) {
                writer.assignNullSerializer(nullArray);
            }
            //字符串
            else if (CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)) {
                writer.assignNullSerializer(nullString);
            }
            //数字
            else if (Number.class.isAssignableFrom(clazz)) {
                writer.assignNullSerializer(nullNumber);
            }
            //boolean
            else if (clazz.equals(Boolean.class)) {
                writer.assignNullSerializer(nullBoolean);
            }
            //日期
            else if (clazz.equals(Date.class)) {
                writer.assignNullSerializer(nullDate);
            }else if(Enum.class.isAssignableFrom(clazz)){
                writer.assignNullSerializer(nullString);
            }
        }
        return beanProperties;
    }

}
