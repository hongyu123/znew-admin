package com.hfw.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author farkle
 * @date 2022-04-08
 */
@SpringBootApplication(scanBasePackages = {"com.hfw.admin","com.hfw.basesystem","com.hfw.model"})
@MapperScan(basePackages = {"com.hfw.basesystem.mapper","com.hfw.service.mapper","com.hfw.admin.mapper"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
