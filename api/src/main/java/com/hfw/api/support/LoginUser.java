package com.hfw.api.support;

import com.hfw.model.entity.AppUser;
import lombok.Data;

/**
 * 当前登录用户
 * @author zyh
 * @date 2022-12-28
 */
@Data
public class LoginUser {

    public static final String login_user = "login_user";
    private Long id;
    private String nickname;
    private String avatar;
    private String phone;
    private String token;
    private Integer enableFlag;

    /**
     * 保存app当前登录用户
     */
    private static final ThreadLocal<LoginUser> contextHolder = new ThreadLocal();
    public static void clearContext() {
        contextHolder.remove();
    }
    public static void setContext(LoginUser loginUser) {
        contextHolder.set(loginUser);
    }
    public static LoginUser getLoginUser() {
        return contextHolder.get();
    }

    public static LoginUser of(AppUser appUser){
        return new LoginUser();
    }
}
