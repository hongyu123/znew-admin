package com.hfw.admin.security;

import com.hfw.basesystem.support.ValidCode;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.common.util.RequestUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有认证通过或授权失败处理
 * @author zyh
 * @date 2022-04-12
 */
@Component
public class AuthenticationHandler implements AuthenticationEntryPoint, AccessDeniedHandler{

    //未认证访问资源调用
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        RequestUtil.json(response, ApiResult.message(ValidCode.NO_LOGIN) );
    }
    //认证通过,没有权限访问资源调用
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        RequestUtil.json(response, ApiResult.message(ValidCode.permission_denied) );
    }
}