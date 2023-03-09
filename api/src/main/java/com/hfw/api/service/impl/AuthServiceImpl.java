package com.hfw.api.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.hfw.api.dto.AppleLoginParam;
import com.hfw.api.dto.PhoneCodeParam;
import com.hfw.api.dto.WeixinLoginParam;
import com.hfw.api.service.AppUserService;
import com.hfw.api.service.AuthService;
import com.hfw.api.support.LoginUser;
import com.hfw.basesystem.entity.AppUser;
import com.hfw.basesystem.entity.AppUserExt;
import com.hfw.basesystem.enums.SmsCodeEnum;
import com.hfw.basesystem.service.AppService;
import com.hfw.basesystem.service.RedisAuthService;
import com.hfw.basesystem.support.ValidCode;
import com.hfw.common.enums.EnableState;
import com.hfw.common.enums.Gender;
import com.hfw.common.support.GeneralException;
import com.hfw.plugins.apple.Apple;
import com.hfw.plugins.weixin.WeixinSNS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author farkle
 * @date 2022-11-26
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {
    @Resource
    private RedisAuthService redisAuthService;
    @Resource
    private AppService appService;
    @Resource
    private AppUserService appUserService;

    @Override
    public void logout(Long userId){
        redisAuthService.logout(userId);
    }
    @Override
    public void logout(String token){
        redisAuthService.logout(token);
    }

    //登录
    private LoginUser login(AppUser appUser){
        if(EnableState.Enable != appUser.getEnableState()){
            throw new GeneralException(ValidCode.DISABLE_ACCOUNT.getCode(), ValidCode.DISABLE_ACCOUNT.getDesc());
        }
        LoginUser loginUser = LoginUser.of(appUser);
        loginUser.setToken(redisAuthService.genToken());

        redisAuthService.store(loginUser.getId(), loginUser.getToken(), loginUser);
        return loginUser;
    }

    @Override
    public LoginUser loginByPhone(PhoneCodeParam loginParam){
        if(!appService.validAndDelIfSuccess(SmsCodeEnum.login, loginParam.getPhone(), loginParam.getCode())){
            throw new GeneralException("验证码错误或已过期!");
        }
        AppUser appUser = appUserService.loginByPhone(loginParam.getPhone());
        return this.login(appUser);
    }

    @Resource
    private WeixinSNS weixinSNS;

    @Transactional
    @Override
    public LoginUser loginByWeixin(WeixinLoginParam loginParam) throws Exception {
        /*JSONObject obj = weixinSNS.access_token(loginParam.getCode());
        String openid = obj.getString("openid");
        JSONObject info = weixinSNS.userinfo(openid, obj.getString("access_token"));*/
        String openid = loginParam.getCode();
        JSONObject info = new JSONObject();
        info.put("nickname","nickname");
        info.put("headimgurl","headimgurl");
        info.put("sex",1);
        AppUser userInfo = new AppUser();
        userInfo.setNickname(info.getString("nickname"));
        userInfo.setAvatar(info.getString("headimgurl"));
        userInfo.setGender(Gender.of(info.getInteger("sex")));
        userInfo.setEnableState(EnableState.Enable);

        AppUserExt ext = new AppUserExt();
        ext.setOpenid(openid);
        AppUser appUser = appUserService.loginByExt(userInfo,ext);
        return this.login(appUser);
    }

    @Resource
    private Apple apple;
    @Override
    public LoginUser loginByApple(AppleLoginParam loginParam) throws Exception {
        //String appleid = apple.verify(loginParam.getToken());
        String appleid = loginParam.getToken();
        AppUserExt ext = new AppUserExt();
        ext.setAppleid(appleid);
        AppUser appUser = appUserService.loginByExt(ext);
        return this.login(appUser);
    }

    @Override
    public Boolean disableUser(Long id){
        return redisAuthService.disableUser(id);
    }

}
