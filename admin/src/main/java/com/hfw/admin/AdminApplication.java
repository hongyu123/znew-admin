package com.hfw.admin;

import cn.xbatis.core.XbatisConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@MapperScan(basePackages = {"com.hfw.model.mapper", "com.hfw.service"})
@SpringBootApplication(scanBasePackages = {"com.hfw.admin", "com.hfw.service"})
public class AdminApplication {

    public static void main(String[] args) {
        XbatisConfig.setDefaultValue("{CREATE_USER}", (entityClass, fieldClass) -> {
         return "admin";
        });
        XbatisConfig.setDefaultValue("{CREATE_TIME}", (entityClass, fieldClass) -> {
            return LocalDateTime.now();
        });
        XbatisConfig.setDefaultValue("{UPDATE_USER}", (entityClass, fieldClass) -> {
            return "admin";
        });
        XbatisConfig.setDefaultValue("{UPDATE_TIME}", (entityClass, fieldClass) -> {
            return LocalDateTime.now();
        });
        SpringApplication.run(AdminApplication.class, args);
    }

}
