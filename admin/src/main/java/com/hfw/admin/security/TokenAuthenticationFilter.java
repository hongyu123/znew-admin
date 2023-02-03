package com.hfw.admin.security;

import com.hfw.basesystem.entity.SysLoginLog;
import com.hfw.basesystem.enums.LogoutType;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.RedisAuthService;
import com.hfw.basesystem.support.ValidCode;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.common.util.RequestUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器
 * @author farkle
 * @date 2022-04-11
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private RedisAuthService redisAuthService;
    @Resource
    private CommonDao commonDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("x-access-token");
        if(!StringUtils.hasText(token)){
            token = request.getParameter("x-access-token");
        }
        if(StringUtils.hasText(token)) {
            LoginUser loginUser = redisAuthService.validToken(token);
            if (loginUser != null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                request.setAttribute("username", loginUser.getUsername());
            } else {
                SysLoginLog log = commonDao.selectOne(new SysLoginLog().setToken(token));
                if (log!=null && LogoutType.pushed_off==log.getLogoutType()) {
                    RequestUtil.json(response, ApiResult.message(ValidCode.NO_LOGIN.getCode(), "您的账号在"+log.getLocation()+"登录,被挤下线!"));
                    return;
                }
            }
        }
        //继续执行下一个过滤器
        filterChain.doFilter(request,response);
    }

}
