package com.hfw.api.service;

import com.hfw.api.dto.AppUserEditDTO;
import com.hfw.api.dto.EditPhoneParam;
import com.hfw.api.support.LoginUser;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.model.entity.AppUser;

/**
 * App用户服务
 * @author zyh
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
