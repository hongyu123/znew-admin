package com.hfw.admin.security;

import com.hfw.basesystem.entity.Auth;
import com.hfw.basesystem.service.SysLoginLogService;
import com.hfw.basesystem.service.impl.RedisAuth;
import com.hfw.basesystem.support.ValidCode;
import com.hfw.common.support.ParamMap;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.common.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 *  spring-security自定义ajax登录处理
 * @author farkle
 * @date 2022-04-11
 */
@Component
public class AjaxLoginHandler implements AuthenticationSuccessHandler ,AuthenticationFailureHandler {

    @Autowired
    private RedisAuth redisAuth;
    @Autowired
    private SysLoginLogService sysLoginLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //设置认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成令牌
        String access_token = UUID.randomUUID().toString().replaceAll("-","");
        //生成刷新令牌，如果accessToken令牌失效，则使用refreshToken重新获取令牌（refreshToken过期时间必须大于accessToken）
        //String refreshToken = "refreshToken";
        loginUser.setToken(access_token);
        //存储
        String pushedOffToken = redisAuth.store(loginUser.getId(), loginUser.getToken(), loginUser);
        //登录日志
        sysLoginLogService.login(loginUser.getToken(),loginUser.getUsername(),"登录成功", request);
        sysLoginLogService.pushedOff(pushedOffToken);
        ParamMap info = ParamMap.create().put("access_token", access_token).put("account", loginUser.getUsername()).put("nickname", loginUser.getNickname())
                .put("avatar",loginUser.getAvatar());
        RequestUtil.json(response, info);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = (String) request.getAttribute("username");
        //String username = request.getParameter("username");
        //UsernameNotFoundException 用户名不存在
        //BadCredentialsException 这个异常一般是用户名或者密码错误
        if (exception instanceof BadCredentialsException){
            sysLoginLogService.login(null,username,"密码错误", request);
            RequestUtil.json(response, ApiResult.error("用户名或密码错误!"));
        }else if(exception instanceof UsernameNotFoundException){
            sysLoginLogService.login(null,username,"用户名不存在", request);
            RequestUtil.json(response,ApiResult.error("用户名或密码错误!"));
        }else if(exception instanceof DisabledException){
            sysLoginLogService.login(null,username,"被禁用的账号", request);
            RequestUtil.json(response, ApiResult.message(ValidCode.DISABLE_ACCOUNT) );
        }else{
            sysLoginLogService.login(null,username,"登录失败:"+exception.getMessage(), request);
            RequestUtil.json(response,ApiResult.error("登录失败:"+exception.getMessage()));
        }
    }

}
