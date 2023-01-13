package com.hfw.api.controller;

import com.hfw.api.dto.AppUserEditDTO;
import com.hfw.api.dto.EditPhoneParam;
import com.hfw.api.service.AppUserService;
import com.hfw.api.service.AuthService;
import com.hfw.api.support.LoginUser;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.basesystem.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * app用户通用
 * @author farkle
 * @date 2022-11-26
 */
@RestController
@RequestMapping("/user/common")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AuthService authService;

    /**
     * 用户信息
     * @return
     */
    @GetMapping("/info")
    public ApiResult<AppUser> userInfo()  {
        AppUser appUser = appUserService.userInfo(LoginUser.getLoginUser().getId());
        return ApiResult.data(appUser);
    }

    /**
     * 校验当前用户的手机号码
     * @param code
     * @return
     */
    @GetMapping("/phone/valid")
    public ApiResult validPhone(@RequestParam String code){
       return appUserService.validPhone(code);
    }

    /**
     * 修改手机号码
     * @param editPhone
     * @return
     */
    @PostMapping("/phone/edit")
    public ApiResult editPhone(@RequestBody @Validated EditPhoneParam editPhone){
        return appUserService.editPhone(editPhone);
    }

    /**
     * 修改用户信息
     * @param dto
     * @return
     */
    @PostMapping("/edit")
    public ApiResult editInfo(@RequestBody @Validated AppUserEditDTO dto){
        return ApiResult.data( appUserService.edit(dto) );
    }

    /**
     * 安全登出
     * @return
     */
    @PostMapping("/logout")
    public ApiResult logout(){
        LoginUser loginUser = LoginUser.getLoginUser();
        authService.logout(loginUser.getId());
        return ApiResult.success();
    }

}
