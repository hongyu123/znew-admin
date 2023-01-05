package com.hfw.admin.controller.sys;

import com.hfw.basesystem.dto.SysLoginLogDTO;
import com.hfw.basesystem.entity.SysLoginLog;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.service.SysLoginLogService;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统登录日志控制器
 * @author zyh
 * @date 2022-12-17
 */
@RestController
@RequestMapping("/sysLoginLog")
public class SysLoginLogController {

    @Autowired
    private CommonService<SysLoginLog> commonService;
    @Autowired
    private SysLoginLogService sysLoginLogService;

    @GetMapping("/page")
    public PageResult page(SysLoginLogDTO dto){
        return sysLoginLogService.page(dto);
    }

    @GetMapping("/detail")
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( commonService.detail(SysLoginLog.class, id) );
    }

}