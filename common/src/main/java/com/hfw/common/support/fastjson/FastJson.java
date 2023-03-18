package com.hfw.common.support.fastjson;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;

/**
 * @author farkle
 * @date 2023-03-17
 */
public class FastJson {

    /**
     * 转换json字符串,不过滤 @JsonIgnore
     * @param object
     * @param features
     * @return
     */
    public static String toJSONString(Object object, com.alibaba.fastjson2.JSONWriter.Feature... features) {
        com.alibaba.fastjson2.JSONWriter.Context writeContext = new com.alibaba.fastjson2.JSONWriter.Context(new ObjectWriterProvider(), features);
        try(JSONWriter jsonWriter = JSONWriter.of(writeContext)){
            ObjectWriter objectWriter = jsonWriter.getObjectWriter(object.getClass());
            objectWriter.write(jsonWriter, object);
            return jsonWriter.toString();
        }
    }

}
