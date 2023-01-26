package com.hfw.admin.controller.sys;

import com.hfw.basesystem.dto.SysAdminLogDTO;
import com.hfw.basesystem.entity.SysAdminLog;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.service.SysAdminLogService;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * admin日志控制器
 * @author farkle
 * @date 2022-12-16
 */
@RestController
@RequestMapping("/sysAdminLog")
public class SysAdminLogController {

    @Resource
    private CommonService<SysAdminLog> commonService;
    @Resource
    private SysAdminLogService sysAdminLogService;

    @GetMapping
    public PageResult page(SysAdminLogDTO dto){
        return sysAdminLogService.page(dto);
    }

    @GetMapping("/{id}")
    public ApiResult detail(@PathVariable("id") Long id){
        return ApiResult.data( commonService.detail(SysAdminLog.class, id) );
    }

}