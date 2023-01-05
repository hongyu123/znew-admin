package com.hfw.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方插件配置
 * @author zyh
 * @date 2022-06-14
 */
//@Configuration
public class PluginConfig {

    @Value("${spring.profiles.active}")
    private String profiles;

}
