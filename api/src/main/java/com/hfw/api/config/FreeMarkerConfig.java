package com.hfw.api.config;

import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author farkle
 * @date 2022-11-25
 */
@Configuration
public class FreeMarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @PostConstruct
    public void setConfigure() throws TemplateModelException {
        configuration.setSharedVariable("contextPath", contextPath);
    }

}
