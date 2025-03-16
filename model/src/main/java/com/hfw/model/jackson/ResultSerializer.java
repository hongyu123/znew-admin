package com.hfw.model.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;

public class ResultSerializer extends JsonSerializer<Result> {
    @Override
    public void serialize(Result result, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("code", result.getCode());
        jsonGenerator.writeStringField("message", result.getMessage());
        DataType dataType = result.getDataType();
        if(result.successful() && dataType!=null){
            if(result.getData()==null){
                switch (dataType){
                    case Obj:
                        jsonGenerator.writeObjectField("data", new Object());
                        break;
                    case Number:
                        jsonGenerator.writeNumberField("data",0);
                        break;
                    case String:
                        jsonGenerator.writeStringField("data","");
                        break;
                    case List:
                        jsonGenerator.writeObjectField("data", new ArrayList<>());
                        break;
                    default:
                        jsonGenerator.writeNullField("data");
                        break;
                }
            }else{
                jsonGenerator.writeObjectField("data", result.getData());
            }
        }
        jsonGenerator.writeEndObject();
    }

}
