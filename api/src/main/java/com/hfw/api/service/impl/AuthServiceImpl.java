package com.hfw.api.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.hfw.api.dto.AppleLoginParam;
import com.hfw.api.dto.PhoneCodeParam;
import com.hfw.api.dto.WeixinLoginParam;
import com.hfw.api.service.AuthService;
import com.hfw.api.support.LoginUser;
import com.hfw.basesystem.config.RedisUtil;
import com.hfw.basesystem.enums.SmsCodeEnum;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.AppService;
import com.hfw.basesystem.service.impl.RedisAuth;
import com.hfw.basesystem.support.ValidCode;
import com.hfw.common.enums.EnableState;
import com.hfw.common.enums.Gender;
import com.hfw.common.support.GeneralException;
import com.hfw.plugins.apple.Apple;
import com.hfw.plugins.weixin.WeixinSNS;
import com.hfw.model.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author zyh
 * @date 2022-11-26
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private RedisAuth redisAuth;
    @Autowired
    private AppService appService;
    @Autowired
    private CommonDao commonDao;

    @Override
    public void logout(Long userId){
        redisAuth.logout(userId);
    }
    @Override
    public void logout(String token){
        redisAuth.logout(token);
    }

    //登录
    private LoginUser login(AppUser appUser){
        if(EnableState.Enable != appUser.getEnableFlag()){
            throw new GeneralException(ValidCode.DISABLE_ACCOUNT.getCode(), ValidCode.DISABLE_ACCOUNT.getDesc());
        }
        String token = UUID.randomUUID().toString().replaceAll("-","");
        LoginUser loginUser = LoginUser.of(appUser);
        loginUser.setToken(token);

        redisAuth.store(loginUser.getId(), loginUser.getToken(), loginUser);
        return loginUser;
    }

    @Override
    public LoginUser loginByPhone(PhoneCodeParam loginParam){
        if(!appService.validCode(SmsCodeEnum.login, loginParam.getPhone(), loginParam.getCode())){
            throw new GeneralException("验证码错误或已过期!");
        }
        AppUser appUser = commonDao.findOne(new AppUser().setPhone(loginParam.getPhone()));
        if(appUser==null){
            appUser = new AppUser().setPhone(loginParam.getPhone()).setEnableFlag(EnableState.Enable);
            commonDao.insert(appUser);
        }
        return this.login(appUser);
    }

    @Autowired
    private WeixinSNS weixinSNS;
    @Override
    public LoginUser loginByWeixin(WeixinLoginParam loginParam) throws Exception {
        JSONObject obj = weixinSNS.access_token(loginParam.getCode());
        String openid = obj.getString("openid");
        JSONObject info = weixinSNS.userinfo(openid, obj.getString("access_token"));
        AppUser appUser = commonDao.findOne(new AppUser().setOpenid(openid));
        if(appUser==null){
            appUser = new AppUser()
                    .setOpenid(openid)
                    .setNickname(info.getString("nickname"))
                    .setAvatar(info.getString("headimgurl"))
                    .setGender(Gender.of(info.getInteger("sex")))
                    .setEnableFlag(EnableState.Enable);
            commonDao.insert(appUser);
        }
        return this.login(appUser);
    }

    @Autowired
    private Apple apple;
    @Override
    public LoginUser loginByApple(AppleLoginParam loginParam) throws Exception {
        String appleId = apple.verify(loginParam.getToken());
        AppUser appUser = commonDao.findOne(new AppUser().setAppleId(appleId));
        if(appUser==null){
            appUser = new AppUser().setAppleId(appleId).setEnableFlag(EnableState.Enable);
            commonDao.insert(appUser);
        }
        return this.login(appUser);
    }

    @Override
    public Boolean disableUser(Long id){
        return redisAuth.disableUser(id);
    }

}
