package com.hfw.api.controller;

import com.hfw.api.support.LoginUser;
import com.hfw.basesystem.config.RedisUtil;
import com.hfw.basesystem.dto.SendCodeParam;
import com.hfw.basesystem.entity.AppAdvice;
import com.hfw.basesystem.entity.AppVersion;
import com.hfw.basesystem.enums.DeviceEnum;
import com.hfw.basesystem.service.AppService;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.plugins.objstore.Qiniu;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 公共服务控制器
 * @author farkle
 * @date 2022-11-26
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    private AppService appService;

    /**
     * 短信验证码发送
     * @param codeParam
     * @return
     */
    @PostMapping("/msg/send/code")
    public ApiResult sendCode(@RequestBody @Validated SendCodeParam codeParam){
        appService.sendCode(codeParam);
        return ApiResult.success();
    }

    /**
     * 七牛云token
     * @return
     */
    //@PostMapping("/qiniu/token")
    public ApiResult qiniuToken(){
        return ApiResult.data( Qiniu.token() );
    }

    /**
     * 产品建议
     * @param advice
     * @return
     */
    @PostMapping("/advice")
    public ApiResult advice(@RequestBody @Validated AppAdvice advice){
        advice.setId(null);
        LoginUser loginUser = LoginUser.getLoginUser();
        advice.setUserId(loginUser==null ?null:loginUser.getId());
        appService.advice(advice);
        return ApiResult.success();
    }

    /**
     * app版本检查更新
     * @param device 设备类型
     * @param version 版本
     * @return
     */
    @GetMapping("/app/version")
    public ApiResult<AppVersion> appVersion(@RequestParam DeviceEnum device, @RequestParam String version){
       return ApiResult.data( appService.appVersion(device, version) );
    }

}
