package com.hfw.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring-security自定义ajax登录配置
 * @author farkle
 * @date 2022-04-12
 */
@Configuration
public class AjaxLoginConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AjaxLoginHandler ajaxLoginHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AjaxLoginFilter filter = new AjaxLoginFilter();
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(ajaxLoginHandler);
        filter.setAuthenticationFailureHandler(ajaxLoginHandler);
        /*//直接使用DaoAuthenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //设置userDetailService
        provider.setUserDetailsService(userDetailsService);
        //设置加密算法
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        http.authenticationProvider(provider);*/
        //将这个过滤器添加到UsernamePasswordAuthenticationFilter之前执行
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

}
