package com.hfw.model.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * jackson自定义null处理
 * @author farkle
 * @date 2022-04-15
 */
public class NullSerializerModifier extends BeanSerializerModifier {

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {

        //循环所有的beanPropertyWriter
        for (BeanPropertyWriter writer : beanProperties) {
            Class<?> clazz = writer.getType().getRawClass();
            //数组
            if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)) {
                writer.assignNullSerializer(new NullSerializer(NullType.NullArray));
            }
            //字符串
            else if (CharSequence.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)) {
                writer.assignNullSerializer(new NullSerializer(NullType.NullString));
            }
            //BigDecimal
            /*else if (clazz.equals(BigDecimal.class)) {
                writer.assignNullSerializer(new NullSerializer(NullType.NullString));
            }
            //数字, 特殊的排序字段
            else if (Number.class.isAssignableFrom(clazz)){
                writer.assignNullSerializer(new NullSerializer(NullType.NullNumber));
            }*/
            //boolean
            else if (clazz.equals(Boolean.class)) {
                writer.assignNullSerializer(new NullSerializer(NullType.NullBoolean));
            }
            //日期
            else if (clazz.equals(Date.class) || clazz.equals(LocalDate.class) || clazz.equals(LocalDateTime.class)) {
                writer.assignNullSerializer(new NullSerializer(NullType.NullDate));
            }
            //枚举
            else if(Enum.class.isAssignableFrom(clazz)){
                writer.assignNullSerializer(new NullSerializer(NullType.NullString));
            }
            /*else{
                writer.assignNullSerializer(new NullSerializer(NullType.NullObject));
            }*/
        }
        return beanProperties;
    }

}
