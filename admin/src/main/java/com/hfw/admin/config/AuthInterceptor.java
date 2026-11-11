package com.hfw.admin.config;

import com.hfw.model.enums.sys.ErrorCode;
import com.hfw.model.jackson.Result;
import com.hfw.model.utils.StrUtil;
import com.hfw.service.auth.AuthService;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    private static final String token = "token";

    private final AuthService authService;
    public AuthInterceptor(AuthService authService){
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 跨域请求(OPTIONS)不认证
        if(RequestMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())){
            // false拦截 true放行
            return false;
        }

        // 登录认证
        LoginUser loginUser = this.loginAuth(request);
        if(loginUser==null){
            RequestUtil.json(response, Result.result(ErrorCode.NO_LOGIN) );
            return false;
        }
        LoginUser.setLoginUser(loginUser);

        if(!this.permissionAuth(loginUser, request)){
            RequestUtil.json(response, Result.result(ErrorCode.PERMISSION_DENIED) );
            return false;
        }
        // 权限认证
        return true;
    }

    private LoginUser loginAuth(HttpServletRequest request){
        String token = request.getHeader(AuthInterceptor.token);
        if(!StringUtils.hasText(token)){
            token = request.getParameter(AuthInterceptor.token);
        }
        try{
            return authService.verifyToken(token);
        }catch (Exception e){
            log.error("verifyToken", e);
        }
        return null;
        /*LoginUser loginUser = new LoginUser();
        loginUser.setId(1L);
        loginUser.setAccount("admin");
        loginUser.setNickname("管理员");
        return loginUser;*/
    }

    private boolean permissionAuth(LoginUser loginUser, HttpServletRequest request){
        if(loginUser.getId()<=1){
            return true;
        }
        Set<String> permission = loginUser.getPermissions();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String key = method+uri;
        //权限直接匹配 method+uri
        if(permission.contains(key)){
            return true;
        }

        //匹配 method/xxx -> method/*
        if(permission.contains( parentPath(key)+"/*" )){
            return true;
        }

        //匹配 /**
        while (StringUtils.hasText(uri)){
            if(permission.contains(uri+"/**")){
                return true;
            }
            uri = parentPath(uri);
        }

        /*//匹配 GET/{id} 和 DELETE/{id}
        String idUri = this.getUriMatchId(request);
        if(idUri!=null && permission.contains(idUri)){
            return true;
        }*/
        return false;
    }

    private String parentPath(String path){
        int index = path.lastIndexOf('/');
        if(index<=0){
            return "";
        }
        return path.substring(0,index);
    }

    /**
     * uri路径 匹配 id
     * /demo/1
     * @param request
     * @return METHOD/demo/id
     */
    private String getUriMatchId(HttpServletRequest request){
        String uri = request.getRequestURI();
        String method = request.getMethod();
        if("GET".equals(method) || "DELETE".equals(method)){
            int index = uri.lastIndexOf('/');
            if(index<=0){
                return null;
            }
            if(StrUtil.isNumeric( uri.substring(index+1) )){
                return method+ uri.substring(0,index) +"/{id}";
            }
        }
        return null;
    }

}
