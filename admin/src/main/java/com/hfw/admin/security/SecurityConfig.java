package com.hfw.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启注解支持
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AjaxLoginConfig ajaxLoginConfig;
    @Autowired
    private AjaxLogoutHandler ajaxLogoutHandler;
    @Autowired
    private AuthenticationHandler authenticationHandler;
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/favicon.ico","/upload/**");
    }

    /**
     * 自定义身份认证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);;
    }

    /**
     * 跨域配置信息源
     *
     * @return
     */
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 设置允许跨域的站点
        corsConfiguration.addAllowedOriginPattern("*");
        // 设置允许跨域的http方法
        corsConfiguration.addAllowedMethod("*");
        // 设置允许跨域的请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许带凭证
        corsConfiguration.setAllowCredentials(true);
        // 对所有的url生效
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //关闭csrf防护
        http.anonymous().disable(); //禁用匿名登录
        http.httpBasic().disable(); //禁用BasicAuthenticationFilter
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//禁用session
        http.formLogin().disable(); //禁用表单登录
        http.cors().configurationSource(corsConfigurationSource());//运行跨域

        http.apply(ajaxLoginConfig); //自定义ajax登录
        http.addFilterBefore(tokenAuthenticationFilter, LogoutFilter.class); //自定义token校验过滤器
        http.logout().logoutUrl("/logout").logoutSuccessHandler(ajaxLogoutHandler);//自定义登出处理

        http.authorizeRequests().antMatchers("/login").permitAll() //放行登录请求
                //基于URL的权限控制
                //.antMatchers("/stu/**").hasAuthority("stu:all")
                //.antMatchers("/test/stu").hasAuthority("stu:all")
                .antMatchers("/auth/**").authenticated()
                .antMatchers("/common/**").authenticated()
                .antMatchers("/appAdvice/unreadCount").authenticated()
                .antMatchers("/**").access("@authorization.check(authentication,request)")//自定义权限校验
                .anyRequest().authenticated(); //所有请求都需要登录认证才能访问;
        http.exceptionHandling().authenticationEntryPoint(authenticationHandler) //未认证访问资源
                .accessDeniedHandler(authenticationHandler); //认证通过,没有权限访问资源

    }

}
