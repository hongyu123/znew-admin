package com.hfw.api.support;

import com.hfw.basesystem.service.impl.RedisAuth;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域处理拦截器,并且获取用户的登录信息
 * @author farkle
 * @date 2022-04-16
 */
@Component
public class CrossAndAuthInterceptor implements HandlerInterceptor {

    private static final String token = "token";

    @Resource
    private RedisAuth redisAuth;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(CrossAndAuthInterceptor.token);
        if(!StringUtils.hasText(token)){
            token = request.getParameter(CrossAndAuthInterceptor.token);
        }
        if(StringUtils.hasText(token)){
            LoginUser loginUser = redisAuth.validToken(token);
            if(loginUser!=null){
                loginUser.setToken(token);
                LoginUser.setContext(loginUser);
                request.setAttribute(LoginUser.login_user, loginUser);
            }
        }
        //跨域处理
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); //针对哪个域名可以访问,不能使用*
        //response.setHeader("Access-Control-Allow-Credentials", "true"); //是否可以携带cookie
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with, sid, mycustom, smuser");
        response.addHeader("Access-Control-Max-Age", "1800");//30 min
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUser.clearContext();
    }

}
