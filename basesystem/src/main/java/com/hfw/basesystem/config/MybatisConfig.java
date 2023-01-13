package com.hfw.basesystem.config;

import com.hfw.basesystem.mybatis.PropertyObjectWrapperFactory;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author farkle
 * @date 2022-12-06
 */
@Configuration
public class MybatisConfig {

    /**
     * configuration 自定义配置
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setObjectWrapperFactory(new PropertyObjectWrapperFactory());
            }
        };
    }

}
