package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.config.RedisUtil;
import com.hfw.basesystem.entity.AppAdvice;
import com.hfw.basesystem.entity.AppArticle;
import com.hfw.basesystem.entity.AppVersion;
import com.hfw.basesystem.enums.DeviceEnum;
import com.hfw.basesystem.enums.SmsCodeEnum;
import com.hfw.basesystem.mapper.AppMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.dto.SendCodeParam;
import com.hfw.basesystem.service.AppService;
import com.hfw.common.support.GeneralException;
import com.hfw.common.util.RandomUtil;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author farkle
 * @date 2022-12-02
 */
@Service("appService")
public class AppServiceImpl implements AppService {

    @Resource
    private CommonDao commonDao;

    @Resource
    private AppMapper appMapper;

    @Resource
    private RedisUtil redisUtil;

    @Value("${spring.profiles.active}")
    private String profiles;

    @Override
    public void sendCode(SendCodeParam codeParam){
        //限制同一手机号码一分钟发送一次
        Boolean res = redisUtil.setNxEx(SmsCodeEnum.redis_key + codeParam.getPhone(), 1, 55);
        if(!res){
            throw new GeneralException("验证码已发送,请稍后再试(1分钟)!");
        }
        String code = RandomUtil.randomNumer(6);
        if("prod".equals(profiles)){
            //TODO:发送短信
        }else{
            code = "123456";
        }
        String key = SmsCodeEnum.redis_key + codeParam.getType().toString()+"_"+codeParam.getPhone();
        redisUtil.setEx(key, code, 30*60);
    }

    @Override
    public boolean validCode(SmsCodeEnum type, String phone, String code){
        String key = SmsCodeEnum.redis_key + type.toString()+"_"+phone;
        String storeCode = redisUtil.get(key);
        return storeCode!=null && storeCode.equals(code);
    }
    @Override
    public boolean validAndDelIfSuccess(SmsCodeEnum type, String phone, String code){
        boolean res = validCode(type, phone, code);
        if(res){
            String key = SmsCodeEnum.redis_key + type.toString()+"_"+phone;
            redisUtil.del(key);
        }
        return res;
    }

    @Override
    public String editPhoneToken(String phone){
        String editToken = UUID.randomUUID().toString().replaceAll("-","");
        redisUtil.setEx("edit_phone_token:"+phone, editToken, 10*60);
        return editToken;
    }
    @Override
    public boolean validEditPhoneToken(String phone, String token){
        String editToken = redisUtil.get("edit_phone_token:"+phone);
        return editToken!=null && editToken.equals(token);
    }

    @Override
    public int advice(AppAdvice advice){
        return commonDao.insert(advice);
    }

    @Override
    public AppVersion appVersion(DeviceEnum device, String version){
        AppVersion appVersion = appMapper.lastVersion(device);
        if(appVersion==null){
            appVersion = new AppVersion();
            appVersion.setVersion(version);
        }
        appVersion.setHasUpdate( version.equals(appVersion.getVersion()) ?0:1 );
        return appVersion;
    }

    @Override
    public AppArticle appArticle(Long id){
        return appMapper.appArticle(id);
    }
}
