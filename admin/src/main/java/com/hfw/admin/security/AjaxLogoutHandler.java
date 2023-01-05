package com.hfw.admin.security;

import com.hfw.basesystem.service.SysLoginLogService;
import com.hfw.basesystem.service.impl.RedisAuth;
import com.hfw.basesystem.support.ValidCode;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.common.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 自定义登出处理
 * @author zyh
 * @date 2022-12-18
 */
@Component
public class AjaxLogoutHandler implements LogoutSuccessHandler {

    @Autowired
    private RedisAuth redisAuth;
    @Autowired
    private SysLoginLogService sysLoginLogService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(authentication==null){
            RequestUtil.json(response, ApiResult.message(ValidCode.NO_LOGIN) );
            return;
        }
        Object principal = authentication.getPrincipal();
        if(principal==null || !(principal instanceof LoginUser) ){
            RequestUtil.json(response, ApiResult.message(ValidCode.NO_LOGIN) );
            return;
        }
        LoginUser loginUser = (LoginUser) principal;
        List<String> tokens = redisAuth.getValidToken(loginUser.getId());
        redisAuth.logout(loginUser.getId());
        sysLoginLogService.logout(tokens);
        RequestUtil.json(response, ApiResult.success() );
    }

}
