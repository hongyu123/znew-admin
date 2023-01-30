package com.hfw.admin.controller.sys;

import com.hfw.admin.log.AdminLog;
import com.hfw.basesystem.dto.AppBannerDTO;
import com.hfw.basesystem.service.AppBannerService;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * app轮播图控制器
 * @author  farkle
 * @date 2023-01-29
 */
@RestController
@RequestMapping("/appBanner")
public class AppBannerController {

    @Resource
    private AppBannerService appBannerService;

    @GetMapping
    public PageResult page(AppBannerDTO dto){
        return appBannerService.page(dto);
    }

    @GetMapping("/{id}")
    public ApiResult detail(@PathVariable("id") Long id){
        return ApiResult.data( appBannerService.detail(id) );
    }

    @AdminLog("新增app轮播图")
    @PostMapping
    public ApiResult add(@RequestBody @Validated(ValidGroup.Add.class) AppBannerDTO appBanner){
        appBannerService.add(appBanner);
        return ApiResult.success();
    }

    @AdminLog("编辑app轮播图")
    @PutMapping
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) AppBannerDTO appBanner){
        appBannerService.edit(appBanner);
        return ApiResult.success();
    }

    @AdminLog("删除app轮播图")
    @DeleteMapping("/{id}")
    public ApiResult del(@PathVariable("id") Long id){
        appBannerService.del(id);
        return ApiResult.success();
    }

    @AdminLog("批量删除app轮播图")
    @DeleteMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        ids.forEach( id-> appBannerService.del(id) );
        return ApiResult.success();
    }

}