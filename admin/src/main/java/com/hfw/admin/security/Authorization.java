package com.hfw.admin.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 自定义路由验证
 * @author zyh
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
        String parent = parentPath(uri);
        return permission.contains(uri) || permission.contains(parent+"/**") || permission.contains( parentPath(parent)+"/**" );
    }

    private String parentPath(String path){
        int index = path.lastIndexOf('/');
        if(index<=0){
            return path;
        }
        return path.substring(0,index);
    }

}
