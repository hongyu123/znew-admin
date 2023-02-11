package com.hfw.admin.security;

import com.hfw.common.util.StrUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 自定义路由验证
 * 不采用@PathVariable
 * @author farkle
 * @date 2022-12-17
 */
@Component
public class Authorization {

    public boolean check(Authentication authentication, HttpServletRequest request) {
        Object principal = authentication.getPrincipal();
        if(principal==null || !(principal instanceof LoginUser) ){
            return false;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if(loginUser.getId()<=1){
            return true;
        }
        Set<String> permission = loginUser.getPermission();
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
