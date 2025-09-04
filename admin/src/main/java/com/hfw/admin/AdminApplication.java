package com.hfw.admin;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;

@MapperScan(basePackages = {"com.hfw.service"}, annotationClass = Mapper.class)
@SpringBootApplication(scanBasePackages = {"com.hfw.admin", "com.hfw.service"}, exclude = FreeMarkerAutoConfiguration.class)
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
