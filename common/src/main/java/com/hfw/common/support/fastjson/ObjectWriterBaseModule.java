package com.hfw.common.support.fastjson;

import com.alibaba.fastjson2.codec.BeanInfo;
import com.alibaba.fastjson2.codec.FieldInfo;
import com.alibaba.fastjson2.modules.ObjectWriterAnnotationProcessor;
import com.alibaba.fastjson2.writer.ObjectWriterProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author farkle
 * @date 2023-03-17
 */
public class ObjectWriterBaseModule extends com.alibaba.fastjson2.writer.ObjectWriterBaseModule {

    private WriterAnnotationProcessor writerAnnotationProcessor;
    public ObjectWriterBaseModule(ObjectWriterProvider provider){
        super(provider);
        this.writerAnnotationProcessor = new WriterAnnotationProcessor();
    }

    @Override
    public ObjectWriterAnnotationProcessor getAnnotationProcessor() {
        return writerAnnotationProcessor;
    }

    public class WriterAnnotationProcessor extends com.alibaba.fastjson2.writer.ObjectWriterBaseModule.WriterAnnotationProcessor {
        @Override
        public void getFieldInfo(BeanInfo beanInfo, FieldInfo fieldInfo, Class objectType, Field field) {
            super.getFieldInfo(beanInfo, fieldInfo, objectType, field);
            Annotation[] annotations = field.getAnnotations();
            if(annotations!=null && annotations.length>0){
                for (int i = 0; i < annotations.length; i++) {
                    Class<? extends Annotation> annotationType = annotations[i].annotationType();
                    if(annotationType==JsonIgnore.class || annotationType== JsonProperty.class){
                        fieldInfo.ignore = false;
                    }
                }
            }
        }
    }
}
