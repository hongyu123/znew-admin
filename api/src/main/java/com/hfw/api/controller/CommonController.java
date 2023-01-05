package com.hfw.api.controller;

import com.hfw.api.support.LoginUser;
import com.hfw.basesystem.dto.SendCodeParam;
import com.hfw.basesystem.entity.AppAdvice;
import com.hfw.basesystem.enums.DeviceEnum;
import com.hfw.basesystem.service.AppService;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.plugins.objstore.Qiniu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 公共服务控制器
 * @author zyh
 * @date 2022-11-26
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private AppService appService;

    //短信验证码发送
    @PostMapping("/msg/send/code")
    public ApiResult sendCode(@RequestBody @Validated SendCodeParam codeParam){
        appService.sendCode(codeParam);
        return ApiResult.success();
    }

    //七牛云token
    @PostMapping("/qiniu/token")
    public ApiResult qiniuToken(){
        return ApiResult.data( Qiniu.token() );
    }

    //产品建议
    @PostMapping("/advice")
    public ApiResult advice(@RequestBody @Validated AppAdvice ad){
        LoginUser loginUser = LoginUser.getLoginUser();
        AppAdvice advice = new AppAdvice().setContent(ad.getContent()).setCategory("产品建议");
        if(loginUser!=null){
            advice.setUserId(loginUser.getId());
        }
        appService.advice(advice);
        return ApiResult.success();
    }

    //问题反馈
    @PostMapping("/feedback")
    public ApiResult feedback(@RequestBody @Validated AppAdvice ad){
        LoginUser loginUser = LoginUser.getLoginUser();
        AppAdvice advice = new AppAdvice().setContent(ad.getContent()).setCategory("问题反馈");
        if(loginUser!=null){
            advice.setUserId(loginUser.getId());
        }
        appService.advice(advice);
        return ApiResult.success();
    }

    //app版本检查更新
    @GetMapping("/app/version")
    public ApiResult appVersion(@RequestParam DeviceEnum device, @RequestParam String version){
       return ApiResult.data( appService.appVersion(device, version) );
    }


}
