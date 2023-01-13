package com.hfw.api.service;

import com.hfw.api.dto.AppUserEditDTO;
import com.hfw.api.dto.EditPhoneParam;
import com.hfw.api.support.LoginUser;
import com.hfw.basesystem.entity.AppUserExt;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.basesystem.entity.AppUser;

/**
 * App用户服务
 * @author farkle
 * @date 2022-11-26
 */
public interface AppUserService {
    /**
     * 用户信息
     * @param id
     * @return
     */
    AppUser userInfo(Long id);

    /**
     * 手机号码登录
     * @param phone
     * @return
     */
    AppUser loginByPhone(String phone);

    /**
     * app用户扩展信息查找
     * @param ext
     * @return
     */
    AppUser findByExt(AppUserExt ext);

    /**
     * 保存app用户及扩展信息
     * @param appUser
     * @param ext
     */
    void save(AppUser appUser, AppUserExt ext);

    /**
     * app用户扩展信息查找
     * @param userInfo
     * @param ext
     * @return
     */
    AppUser loginByExt(AppUser userInfo, AppUserExt ext);

    /**
     * app用户扩展信息查找
     * @param ext
     * @return
     */
    AppUser loginByExt(AppUserExt ext);

    /**
     * 校验当前用户的手机号码
     * @param code
     * @return
     */
    ApiResult validPhone(String code);

    /**
     * 修改手机号码
     * @param editPhone
     * @return
     */
    ApiResult editPhone(EditPhoneParam editPhone);


    /**
     * 修改用户信息
     * @param dto
     * @return
     */
    LoginUser edit(AppUserEditDTO dto);

}
