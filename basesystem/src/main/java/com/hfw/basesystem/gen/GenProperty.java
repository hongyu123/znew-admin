package com.hfw.basesystem.gen;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 代码生成配置属性
 * @author zyh
 * @date 2023-01-03
 */
@Data
@Component
@ConfigurationProperties("gen")
public class GenProperty {
    private String pack;
    private String author;
    private String path;
    private String tablePrefix;
    private String db;
    private String logicDeleteField;
}
