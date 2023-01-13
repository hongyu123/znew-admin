package com.hfw.common.util;

/**
 * 正则校验工具类
 * @author farkle
 * @date 2022-04-06
 */
public class ValidUtil {
    public static final String phoneReg = "^\\d{11}$";
    public static final String emailReg = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String passwordReg = "^\\w{6,16}$";
    /**
     * 手机号码校验 11位
     * @param phone 手机号
     * @return
     */
    public static boolean validPhone(String phone) {
        if(phone == null) {
            return false;
        }
        return phone.matches(phoneReg);
    }
    /**
     * 密码校验 "^\\w{6,16}$"
     * @param password 密码
     * @return
     */
    public static boolean validPassword(String password) {
        if(password == null) {
            return false;
        }
        return password.matches(passwordReg);
    }

    /**
     * 昵称校验 "^.{2,16}$"
     * @param nickname 昵称 2-16个字符
     * @return
     */
    public static boolean validNickname(String nickname){
        if(nickname == null) {
            return false;
        }
        String reg = "^.{2,16}$";
        return nickname.matches(reg);
    }

    /**
     * 邮箱校验
     * @param email
     * @return
     */
    public static boolean validEmail(String email) {
        if(email == null) {
            return false;
        }
        return email.matches(emailReg);
    }
}
