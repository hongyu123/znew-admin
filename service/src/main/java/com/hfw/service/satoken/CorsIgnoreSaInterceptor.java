package com.hfw.service.satoken;

import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.interceptor.SaInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 忽略跨域预检查请求
 */
public class CorsIgnoreSaInterceptor extends SaInterceptor {
    public CorsIgnoreSaInterceptor(){
        super();
    }
    public CorsIgnoreSaInterceptor(SaParamFunction<Object> auth) {
        super(auth);
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(RequestMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())){
            return false;
        }
        return super.preHandle(request, response, handler);
    }

}
