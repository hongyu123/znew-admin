package com.hfw.model.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * null处理
 */
public class NullSerializer extends JsonSerializer<Object> {
    private final NullType nullType;
    public NullSerializer(NullType nullType){
        this.nullType = nullType;
    }
    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        switch (nullType){
            case NullArray:
                jsonGenerator.writeStartObject();
                jsonGenerator.writeEndObject();
                break;
            case NullString:
                jsonGenerator.writeString("");
                break;
            case NullNumber:
                jsonGenerator.writeNumber(0);
                break;
            case NullBoolean:
                jsonGenerator.writeBoolean(false);
                break;
            case NullDate:
                jsonGenerator.writeString("");
                break;
            case NullObject:
                jsonGenerator.writeObject(new Object());
                break;
            default:
                jsonGenerator.writeNull();
                break;
        }
    }

}
