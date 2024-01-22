package com.hfw.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hfw.basesystem.support.LocalDateConverter;
import com.hfw.basesystem.support.LocalDateTimeConverter;
import com.hfw.common.support.jackson.NullHandleObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author farkle
 * @create 2020-03-03
 */
@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    @Value("${upload-path}")
    private String uploadPath;
    //静态资源路径映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+uploadPath);
    }

    //jackson返回配置
    @Bean
    public ObjectMapper objectMapper(){
        return NullHandleObjectMapper.getInstance();
    }
    /*@Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        return new NullHandleMappingJackson2HttpMessageConverter();
    }*/

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConverter());
        registry.addConverter(new LocalDateTimeConverter());
    }

}
