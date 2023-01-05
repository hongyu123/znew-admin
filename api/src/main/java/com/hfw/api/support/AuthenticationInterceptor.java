package com.hfw.api.support;

import com.hfw.basesystem.support.ValidCode;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.common.util.RequestUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证拦截器
 * @author zyh
 * @date 2022-04-16
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        LoginUser loginUser = (LoginUser)request.getAttribute(LoginUser.login_user);
        if(loginUser==null){
            RequestUtil.json(response, ApiResult.message(ValidCode.NO_LOGIN) );
            return false;
        }
        if(loginUser.getEnableFlag()!=1){
            RequestUtil.json(response, ApiResult.message(ValidCode.DISABLE_ACCOUNT) );
            return false;
        }
        return true;
    }

}
