package com.hfw.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hfw.model.jackson.ObjectMapperBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {
    @Value("${upload.storage-path}")
    private String storagePath;

    //静态资源路径映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+storagePath);
    }


    //jackson返回配置
    @Bean
    public ObjectMapper objectMapper(){
        return ObjectMapperBuilder.buildSingleton();
    }

    //跨域处理
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")   //允许跨域的请求路径
                .allowedOrigins("*") // 允许的跨域源，可以通过逗号隔开，也可以用 * 表示允许所有源；
                .allowedMethods("*").allowedHeaders("*")
                //.allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // 允许的请求方法
                //.allowedHeaders("content-type", "x-requested-with", "authorization") // 允许的请求头类型
                .maxAge(1800);    // 设置请求最大有效时长，在这个时长内，重复的请求就不会发送预检请求
    }

}
