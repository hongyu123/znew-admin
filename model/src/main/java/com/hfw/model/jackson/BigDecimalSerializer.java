package com.hfw.model.jackson;

import com.hfw.model.utils.NumberUtil;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.math.BigDecimal;

/**
 * 自定义序列化BigDecimal
 * @author farkle
 * @date 2022-11-10
 */
public class BigDecimalSerializer extends ValueSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        gen.writeString(NumberUtil.scale2(value));
    }

}
