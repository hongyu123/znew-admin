package com.hfw.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;

@MapperScan(basePackages = {"com.hfw.model.mapper", "com.hfw.service"})
@SpringBootApplication(scanBasePackages = {"com.hfw.admin", "com.hfw.service"}, exclude = FreeMarkerAutoConfiguration.class)
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
