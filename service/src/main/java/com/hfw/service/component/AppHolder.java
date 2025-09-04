package com.hfw.service.component;

import cn.dev33.satoken.stp.StpUtil;
import cn.xbatis.core.XbatisGlobalConfig;
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
        XbatisGlobalConfig.setDynamicValue("{CREATE_USER}", (entityClass, fieldClass) -> {
            return StpUtil.isLogin() ?LoginUser.getLoginUser().getAccount() :"";
        });
        XbatisGlobalConfig.setDynamicValue("{CREATE_TIME}", (entityClass, fieldClass) -> {
            return LocalDateTime.now();
        });
        XbatisGlobalConfig.setDynamicValue("{UPDATE_USER}", (entityClass, fieldClass) -> {
            return StpUtil.isLogin() ?LoginUser.getLoginUser().getAccount() :"";
        });
        XbatisGlobalConfig.setDynamicValue("{UPDATE_TIME}", (entityClass, fieldClass) -> {
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
