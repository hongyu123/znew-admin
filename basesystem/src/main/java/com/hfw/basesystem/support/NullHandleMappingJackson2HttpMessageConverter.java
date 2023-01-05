package com.hfw.basesystem.support;

import com.hfw.common.support.jackson.NullHandleObjectMapper;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 自定义jackson消息响应处理
 */
public class NullHandleMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public NullHandleMappingJackson2HttpMessageConverter() {
        //getObjectMapper().setSerializerFactory(getObjectMapper().getSerializerFactory().withSerializerModifier(new NullSerializerModifier()));
        super(NullHandleObjectMapper.getInstance());
    }
}
