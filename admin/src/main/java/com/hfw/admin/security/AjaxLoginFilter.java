package com.hfw.admin.security;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hfw.basesystem.config.RedisUtil;
import com.hfw.basesystem.support.ValidCode;
import com.hfw.common.support.GeneralException;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.common.util.RequestUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * spring-security自定义ajax登录过滤器
 * @author farkle
 * @date 2022-04-08
 */
public class AjaxLoginFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 构造方法，调用父类的，设置登录地址/login，请求方式POST
     */
    public AjaxLoginFilter(RedisUtil redisUtil) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.redisUtil = redisUtil;
    }

    private RedisUtil redisUtil;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        /*获取表单提交数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");*/
        JSONObject obj = JSON.parseObject(request.getInputStream(), StandardCharsets.UTF_8);
        String username = obj.getString("username");
        String password = obj.getString("password");
        String captcha =  obj.getString("captcha");
        if(!StringUtils.hasText(captcha) || redisUtil.get("captcha:"+captcha)==null){
            throw new GeneralException(ValidCode.CAPTCHA_FAIL.getCode(), ValidCode.CAPTCHA_FAIL.getDesc());
        }
        redisUtil.del("captcha:"+captcha);
        //封装到token中提交
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,password);
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            super.doFilter(request, response, chain);
        }catch (GeneralException e){
            RequestUtil.json((HttpServletResponse) response, ApiResult.message(e.getCode(), e.getMessage()) );
        }
    }
}

