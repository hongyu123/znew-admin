package com.hfw.basesystem.service;

/**
 * 属性配置服务
 * @author farkle
 * @date 2023-01-29
 */
public interface SysConfigService {

    /**
     * 获取key
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置key
     * @param key
     * @param value
     */
    void set(String key, String value);
}
