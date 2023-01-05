package com.hfw.admin.controller.sys;

import com.hfw.basesystem.dto.SysAdminLogDTO;
import com.hfw.basesystem.entity.SysAdminLog;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.service.SysAdminLogService;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * admin日志控制器
 * @author zyh
 * @date 2022-12-16
 */
@RestController
@RequestMapping("/sysAdminLog")
public class SysAdminLogController {

    @Autowired
    private CommonService<SysAdminLog> commonService;
    @Autowired
    private SysAdminLogService sysAdminLogService;

    @GetMapping("/page")
    public PageResult page(SysAdminLogDTO dto){
        return sysAdminLogService.page(dto);
    }

    @GetMapping("/detail")
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( commonService.detail(SysAdminLog.class, id) );
    }

}