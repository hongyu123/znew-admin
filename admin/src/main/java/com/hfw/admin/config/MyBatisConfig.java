package com.hfw.admin.config;

import com.github.pagehelper.PageInterceptor;
import com.hfw.service.component.DataScopeInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MyBatisConfig {
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            // 添加数据权限处理插件
            DataScopeInterceptor plugin = new DataScopeInterceptor();
            configuration.addInterceptor(plugin);

            // PageHelper分页
            Properties properties = new Properties();
            properties.setProperty("helperDialect","postgresql");
            PageInterceptor pageInterceptor = new PageInterceptor();
            pageInterceptor.setProperties(properties);
            configuration.addInterceptor(pageInterceptor);
        };
    }
}
