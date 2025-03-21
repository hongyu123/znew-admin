package com.hfw.service.dto;

import cn.dev33.satoken.stp.StpUtil;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.po.sys.SysUser;
import com.hfw.model.utils.LocalDateUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class LoginUser {
    private static final String USER = "login_user";

    private Long id;
    private String avatar;
    private String account;
    private String username; //保留,给前端用
    private String nickname;
    private List<String> permissions;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expires;

    public static LoginUser of(SysUser sysUser){
        LoginUser loginUser = new LoginUser();
        loginUser.setId(sysUser.getId());
        loginUser.setAvatar(sysUser.getAvatar());
        loginUser.setAccount(sysUser.getAccount());
        loginUser.setUsername(sysUser.getAccount());
        loginUser.setNickname(sysUser.getNickname());
        loginUser.setPermissions(null);
        StpUtil.login(sysUser.getId());
        return loginUser;
    }
    public static void store(LoginUser loginUser){
        loginUser.setAccessToken( StpUtil.getTokenValue() );
        long timeout = System.currentTimeMillis() + StpUtil.getTokenTimeout()*1000; //获取当前会话剩余有效期（单位：s，返回-1代表永久有效）
        loginUser.setExpires(LocalDateUtil.toLocalDateTime(timeout));
        StpUtil.getSession().set(USER, loginUser);
    }

    public static LoginUser getLoginUser(){
        StpUtil.checkLogin();
        return  (LoginUser) StpUtil.getSession().get(USER);
    }

    public static void enableUser(Long userId, EnableState state){
        if(state==EnableState.Enable){
            StpUtil.untieDisable(userId);
        }else{
            // 先踢下线
            StpUtil.kickout(userId);
            // 再封禁账号, 封禁时间单位:秒,-1代表永久封禁
            StpUtil.disable(userId, -1);
        }
    }
}
