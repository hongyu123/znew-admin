package com.hfw.api.support;

import com.hfw.basesystem.entity.AppUser;
import com.hfw.common.enums.EnableState;
import lombok.Data;

/**
 * 当前登录用户
 * @author farkle
 * @date 2022-12-28
 */
@Data
public class LoginUser {

    public static final String login_user = "login_user";

    /** id */
    private Long id;
    /** 昵称 */
    private String nickname;
    /** 头像 */
    private String avatar;
    /** 手机号码 */
    private String phone;
    /** token */
    private String token;
    /** 启用状态 */
    private EnableState enableState;

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
        LoginUser loginUser = new LoginUser();
        loginUser.setId(appUser.getId());
        loginUser.setNickname(appUser.getNickname());
        loginUser.setAvatar(appUser.getAvatar());
        loginUser.setPhone(appUser.getPhone());
        loginUser.setEnableState(appUser.getEnableState());
        return loginUser;
    }
}
