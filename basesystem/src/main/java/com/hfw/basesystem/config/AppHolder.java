package com.hfw.basesystem.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zyh
 * @date 2022-11-09
 */
@Component
public class AppHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppHolder.applicationContext = applicationContext;
    }

    private static void checkApplicationContext() {
        if (AppHolder.applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入");
        }
    }

    public static ApplicationContext getApplicationContext() {
        AppHolder.checkApplicationContext();
        return AppHolder.applicationContext;
    }

}
