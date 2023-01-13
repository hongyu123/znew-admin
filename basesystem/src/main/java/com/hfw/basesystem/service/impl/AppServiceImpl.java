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
import com.hfw.common.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author farkle
 * @date 2022-12-02
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${spring.profiles.active}")
    private String profiles;

    @Override
    public void sendCode(SendCodeParam codeParam){
        /**
         * TODO:发送短信
         */
        String code = RandomUtil.randomNumer(6);
        if(!"prod".equals(profiles)){
            code = "123456";
        }
        String key = SmsCodeEnum.redis_key + codeParam.getType().toString()+"_"+codeParam.getPhone();
        redisUtil.set(key, code, 30*60);
    }

    @Override
    public boolean validCode(SmsCodeEnum type, String phone, String code){
        String key = SmsCodeEnum.redis_key + type.toString()+"_"+phone;
        String storeCode = redisUtil.get(key);
        boolean res = storeCode!=null && storeCode.equals(code);
        if(res){
            redisUtil.del(key);
        }
        return res;
    }
    @Override
    public String editPhoneToken(String phone){
        String editToken = UUID.randomUUID().toString().replaceAll("-","");
        redisUtil.set("edit_phone_token:"+phone, editToken, 10*60);
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
            return new AppVersion().setVersion(version).setHasUpdate(0);
        }
        appVersion.setHasUpdate( version.equals(appVersion.getVersion()) ?0:1 );
        return appVersion;
    }

    @Override
    public AppArticle appArticle(Long id){
        return appMapper.appArticle(id);
    }
}
