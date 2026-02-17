package com.hfw.service.component;

import cn.dev33.satoken.stp.StpUtil;
import com.hfw.model.mybatis.MybatisGlobalConfig;
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
        MybatisGlobalConfig.registerDynamic("$NOW", type -> {
            if(LocalDateTime.class == type){
                return LocalDateTime.now();
            }
            return null;
        });
        MybatisGlobalConfig.registerDynamic("$CURRENT_USER", type -> {
            if(String.class == type){
                return StpUtil.isLogin() ?LoginUser.getLoginUser().getAccount() :"";
            }
            return null;
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
