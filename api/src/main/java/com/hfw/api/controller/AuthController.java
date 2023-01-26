package com.hfw.api.controller;

import com.hfw.api.dto.AppleLoginParam;
import com.hfw.api.dto.PhoneCodeParam;
import com.hfw.api.dto.WeixinLoginParam;
import com.hfw.api.service.AuthService;
import com.hfw.api.support.LoginUser;
import com.hfw.common.support.jackson.ApiResult;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 * @author farkle
 * @date 2022-11-25
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    /**
     * 手机号码登录
     * @param loginParam 手机号码登录参数
     * @return
     */
    @PostMapping("/login/phone")
    public ApiResult<LoginUser> phoneLogin(@RequestBody @Validated PhoneCodeParam loginParam) {
        return ApiResult.data( authService.loginByPhone(loginParam) );
    }

    /**
     * 微信登录
     * @param loginParam
     * @return
     * @throws Exception
     */
    @PostMapping("/login/weixin")
    public  ApiResult<LoginUser> weixinLogin(@RequestBody @Validated WeixinLoginParam loginParam) throws Exception {
        return ApiResult.data( authService.loginByWeixin(loginParam) );
    }

    /**
     * 苹果登录
     * @param loginParam
     * @return
     * @throws Exception
     */
    @PostMapping("/login/apple")
    public ApiResult<LoginUser> appleLogin(@RequestBody @Validated AppleLoginParam loginParam) throws Exception {
        return ApiResult.data( authService.loginByApple(loginParam) );
    }

}
