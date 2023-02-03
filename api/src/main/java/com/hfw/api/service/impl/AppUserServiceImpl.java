package com.hfw.api.service.impl;

import com.hfw.api.dto.AppUserEditDTO;
import com.hfw.api.dto.EditPhoneParam;
import com.hfw.api.mapper.AppUserMapper;
import com.hfw.api.service.AppUserService;
import com.hfw.api.support.LoginUser;
import com.hfw.basesystem.entity.AppUserExt;
import com.hfw.basesystem.enums.SmsCodeEnum;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.AppService;
import com.hfw.basesystem.service.RedisAuthService;
import com.hfw.common.enums.EnableState;
import com.hfw.common.support.GeneralException;
import com.hfw.common.support.ParamMap;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.basesystem.entity.AppUser;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author farkle
 * @date 2022-11-26
 */
@Service("appUserService")
public class AppUserServiceImpl implements AppUserService {
    @Resource
    private CommonDao commonDao;
    @Resource
    private AppService appService;
    @Resource
    private RedisAuthService redisAuthService;
    @Resource
    private AppUserMapper appUserMapper;

    @Override
    public AppUser userInfo(Long id){
        AppUser user = commonDao.selectByPk(AppUser.class, id);
        return user;
    }

    @Override
    public AppUser loginByPhone(String phone){
        AppUser appUser = commonDao.selectOne(new AppUser().setPhone(phone));
        if(appUser==null){
            appUser = new AppUser().setPhone(phone).setEnableState(EnableState.Enable);
            commonDao.insert(appUser);
        }
        return appUser;
    }

    @Override
    public AppUser findByExt(AppUserExt ext){
        return appUserMapper.findByExt(ext);
    }

    @Transactional
    @Override
    public void save(AppUser appUser, AppUserExt ext){
        commonDao.insert(appUser);
        ext.setUserId(appUser.getId());
        commonDao.insert(ext);
    }

    @Transactional
    @Override
    public AppUser loginByExt(AppUser userInfo, AppUserExt ext){
        AppUser appUser = this.findByExt(ext);
        if(appUser==null){
            appUser = userInfo;
            this.save(appUser, ext);
        }
        return appUser;
    }

    @Transactional
    @Override
    public AppUser loginByExt(AppUserExt ext){
        return this.loginByExt(new AppUser().setEnableState(EnableState.Enable), ext);
    }


    @Override
    public ApiResult validPhone(String code){
        LoginUser loginUser = LoginUser.getLoginUser();
        if(!appService.validCode(SmsCodeEnum.valid_phone, loginUser.getPhone(), code)){
            return ApiResult.error("验证码错误或已过期!");
        }
        String editToken = appService.editPhoneToken(loginUser.getPhone());
        return ApiResult.data(ParamMap.create().put("editToken",editToken));
    }
    @Override
    public ApiResult editPhone(EditPhoneParam editPhone){
        LoginUser loginUser = LoginUser.getLoginUser();
        if(!appService.validEditPhoneToken(loginUser.getPhone(), editPhone.getEditToken())){
            return ApiResult.error("验证码已过期!请先校验当前用户的手机号码");
        }
        if(!appService.validCode(SmsCodeEnum.edit_phone, editPhone.getPhone(), editPhone.getCode())){
            return ApiResult.error("验证码错误!");
        }
        Long cnt = commonDao.count(new AppUser().setPhone(editPhone.getPhone()));
        if(cnt>0){
            throw new GeneralException("该手机号码已存在!");
        }
        AppUser user = new AppUser().setId(loginUser.getId()).setPhone(editPhone.getPhone());
        commonDao.updateByPk(user);
        loginUser.setPhone(editPhone.getPhone());
        redisAuthService.update(loginUser.getId(), loginUser);
        return ApiResult.success();
    }

    @Override
    public LoginUser edit(AppUserEditDTO dto){
        LoginUser loginUser = LoginUser.getLoginUser();
        AppUser appUser = AppUserEditDTO.to(dto);
        appUser.setId(loginUser.getId());
        commonDao.updateByPk(appUser);

        if( StringUtils.hasText(dto.getNickname()) ){
            loginUser.setNickname(dto.getNickname());
        }
        if( StringUtils.hasText(dto.getAvatar()) ){
            loginUser.setAvatar(dto.getAvatar());
        }
        redisAuthService.update(loginUser.getId(), loginUser);
        return loginUser;
    }

}
