package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.po.sys.SysAdminLog;
import com.hfw.service.sys.sysAdminLog.SysAdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * admin日志Controller
 * @author  farkle
 * @date 2025-03-22
 */
@RestController
@RequestMapping("/sysAdminLog")
public class SysAdminLogController {
    @Autowired
    private SysAdminLogService sysAdminLogService;

    @SaCheckPermission("sysAdminLog:page")
    @GetMapping("/page")
    public PageResult<SysAdminLog> page(Page<SysAdminLog> page, SysAdminLog po) {
        return PageResult.of(sysAdminLogService.page(page, po));
    }

}
