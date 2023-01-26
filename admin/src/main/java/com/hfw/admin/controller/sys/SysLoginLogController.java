package com.hfw.admin.controller.sys;

import com.hfw.basesystem.dto.SysLoginLogDTO;
import com.hfw.basesystem.entity.SysLoginLog;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.service.SysLoginLogService;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统登录日志控制器
 * @author farkle
 * @date 2022-12-17
 */
@RestController
@RequestMapping("/sysLoginLog")
public class SysLoginLogController {

    @Resource
    private CommonService<SysLoginLog> commonService;
    @Resource
    private SysLoginLogService sysLoginLogService;

    @GetMapping
    public PageResult page(SysLoginLogDTO dto){
        return sysLoginLogService.page(dto);
    }

    @GetMapping("/{id}")
    public ApiResult detail(@PathVariable("id") Long id){
        return ApiResult.data( commonService.detail(SysLoginLog.class, id) );
    }

}