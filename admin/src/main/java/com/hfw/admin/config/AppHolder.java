package com.hfw.admin.config;

import cn.dev33.satoken.stp.StpUtil;
import cn.xbatis.core.XbatisConfig;
import com.hfw.service.dto.LoginUser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * ApplicationContext 获取工具
 * @author farkle
 * @date 2022-11-09
 */
@Component
public class AppHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppHolder.applicationContext = applicationContext;
        XbatisConfig.setDefaultValue("{CREATE_USER}", (entityClass, fieldClass) -> {
            return StpUtil.isLogin() ?LoginUser.getLoginUser().getAccount() :"";
        });
        XbatisConfig.setDefaultValue("{CREATE_TIME}", (entityClass, fieldClass) -> {
            return LocalDateTime.now();
        });
        XbatisConfig.setDefaultValue("{UPDATE_USER}", (entityClass, fieldClass) -> {
            return StpUtil.isLogin() ?LoginUser.getLoginUser().getAccount() :"";
        });
        XbatisConfig.setDefaultValue("{UPDATE_TIME}", (entityClass, fieldClass) -> {
            return LocalDateTime.now();
        });
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
