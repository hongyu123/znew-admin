package com.hfw.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zyh
 * @date 2022-04-08
 */
@SpringBootApplication(scanBasePackages = {"com.hfw.api","com.hfw.basesystem","com.hfw.service"})
@MapperScan(basePackages = {"com.hfw.basesystem.mapper","com.hfw.service.mapper","com.hfw.api.mapper"})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
