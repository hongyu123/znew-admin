package com.hfw.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hfw.model.po.sys.SysUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class LoginUser {
    private static final String USER = "login_user";

    private Long id;
    private Long orgId;
    private String avatar;
    private String account;
    private String username; //保留,给前端用
    private String nickname;
    private Set<String> permissions;
    private Set<String> roles; //保留,给前端用
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expires;

    public boolean isAdmin(){
        return this.id==1;
    }

    public static LoginUser of(SysUser sysUser){
        LoginUser loginUser = new LoginUser();
        loginUser.setId(sysUser.getId());
        loginUser.setOrgId(sysUser.getOrgId());
        loginUser.setAvatar(sysUser.getAvatar());
        loginUser.setAccount(sysUser.getAccount());
        loginUser.setUsername(sysUser.getAccount());
        loginUser.setNickname(sysUser.getNickname());
        loginUser.setPermissions(null);
        return loginUser;
    }

    // 保存当前登录用户
    private static final ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();
    public static void clear() {
        threadLocal.remove();
    }
    public static void setLoginUser(LoginUser loginUser) {
        threadLocal.set(loginUser);
    }
    public static LoginUser getLoginUser() {
        return threadLocal.get();
    }

    @JsonIgnore
    //数据权限key
    private String dataScopeKey;
    public static void enableDataScope(String key){
//        StpUtil.checkLogin();
//        LoginUser loginUser = (LoginUser) StpUtil.getSession().get(USER);
//        loginUser.dataScopeKey = key;
    }

}
