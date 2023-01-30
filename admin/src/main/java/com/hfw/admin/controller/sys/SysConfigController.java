package com.hfw.admin.controller.sys;

import com.hfw.admin.log.AdminLog;
import com.hfw.basesystem.enums.SysConfigEnum;
import com.hfw.basesystem.service.SysConfigService;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 属性配置
 * @author farkle
 * @date 2023-01-29
 */
@RestController
@RequestMapping("/sysConfig")
public class SysConfigController {

    @Resource
    private SysConfigService sysConfigService;

    @GetMapping
    public ApiResult gets(){
        Map<String,Object> values = new HashMap<>();
        for(SysConfigEnum config : SysConfigEnum.values()){
            String key = config.getCode();
            String value = sysConfigService.get(key);
            values.put(key,value);
        }
        return ApiResult.data(values);
    }

    @AdminLog("系统配置修改")
    @PostMapping
    public ApiResult sets(@RequestBody Map<String,String> params){
        for(SysConfigEnum config : SysConfigEnum.values()){
            String key = config.getCode();
            String value = params.get(key);
            if(StringUtils.hasText(value)){
                sysConfigService.set(key,value);
            }
        }
        return ApiResult.success();
    }

}
