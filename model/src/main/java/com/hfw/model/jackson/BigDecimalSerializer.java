package com.hfw.model.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hfw.model.utils.NumberUtil;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 自定义序列化BigDecimal
 * @author farkle
 * @date 2022-11-10
 */
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(NumberUtil.scale2(bigDecimal));
    }

}
