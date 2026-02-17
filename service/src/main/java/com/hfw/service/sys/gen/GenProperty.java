package com.hfw.service.sys.gen;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 代码生成配置属性
 * @author farkle
 * @date 2023-01-03
 */
@Getter
@Setter
@Component
@ConfigurationProperties("gen")
public class GenProperty {
    private String pack;
    private String author;
    private String path;
    private String tablePrefix;
    private String schema;
    private String projectName;
    private String logicDeleteField;
}
