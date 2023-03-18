package com.hfw.common.support.fastjson;

/**
 * @author farkle
 * @date 2023-03-17
 */
public class ObjectWriterProvider extends com.alibaba.fastjson2.writer.ObjectWriterProvider {
    @Override
    public void init() {
        //super.init();
        super.register(new ObjectWriterBaseModule(this));
    }

}
