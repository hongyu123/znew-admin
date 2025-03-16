package com.hfw.admin.controller.sys;

import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysGenTable;
import com.hfw.service.sys.gen.SysGenTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 表单生成记录Controller
 * @author  farkle
 * @date 2025-03-16
 */
@RestController
@RequestMapping("/sysGenTable")
public class SysGenTableController {
    @Autowired
    private SysGenTableService sysGenTableService;

    @GetMapping("/page")
    public PageResult<SysGenTable> page(Page<SysGenTable> page, SysGenTable po) {
        return PageResult.of(sysGenTableService.page(page, po));
    }

    @GetMapping
    public Result<SysGenTable> detail(@RequestParam Long id){
        return Result.success( sysGenTableService.detail( id) );
    }

    @AdminLog("新增表单生成记录")
    @PostMapping
    public Result<Void> add(@RequestBody SysGenTable sysGenTable){
        sysGenTableService.saveGenFormRecord(sysGenTable);
        return Result.success();
    }

}
