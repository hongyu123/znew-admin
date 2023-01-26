package com.hfw.admin.controller.sys;

import com.hfw.admin.log.AdminLog;
import com.hfw.basesystem.dto.AppVersionDTO;
import com.hfw.basesystem.entity.AppVersion;
import com.hfw.basesystem.service.AppVersionService;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * app版本管理控制器
 * @author farkle
 * @date 2022-12-20
 */
@RestController
@RequestMapping("/appVersion")
public class AppVersionController {

    @Resource
    private CommonService<AppVersion> commonService;
    @Resource
    private AppVersionService appVersionService;

    @GetMapping
    public PageResult page(AppVersionDTO dto){
        return appVersionService.page(dto);
    }

    @GetMapping("/{id}")
    public ApiResult detail(@PathVariable("id") Long id){
        return ApiResult.data( commonService.detail(AppVersion.class, id) );
    }

    @AdminLog("新增APP版本")
    @PostMapping
    public ApiResult save(@RequestBody @Validated(ValidGroup.Add.class) AppVersion appVersion){
        commonService.save(appVersion);
        return ApiResult.success();
    }

    @AdminLog("编辑APP版本")
    @PutMapping
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) AppVersion appVersion){
        commonService.edit(appVersion);
        return ApiResult.success();
    }

    @AdminLog("删除APP版本")
    @DeleteMapping("/{id}")
    public ApiResult del(@PathVariable("id") Long id){
        commonService.del(AppVersion.class, id);
        return ApiResult.success();
    }

    @AdminLog("批量APP版本")
    @DeleteMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        commonService.dels(AppVersion.class, ids);
        return ApiResult.success();
    }

}