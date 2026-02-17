package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import com.hfw.model.mybatis.Where;
import com.hfw.model.po.sys.SysGenTable;
import com.hfw.service.component.CommonService;
import com.hfw.service.sys.gen.SysGenTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 表单生成记录Controller
 * @author  farkle
 * @date 2025-03-16
 */
@RestController
@RequestMapping("/sysGenTable")
public class SysGenTableController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private SysGenTableService sysGenTableService;

    @SaCheckPermission("sysGenTable:page")
    @GetMapping("/page")
    public PageResult<SysGenTable> page(Page<SysGenTable> page, SysGenTable po) {
        Map<String, String> params = page.getParams();
        Where<SysGenTable> where = Where.<SysGenTable>where()
                .eq(po.getId() != null, SysGenTable.COLUMN.id, po.getId())
                .eq(StringUtils.hasText(po.getTableName()), SysGenTable.COLUMN.tableName, po.getTableName())
                .like(StringUtils.hasText(params.get("tableName_like")), SysGenTable.COLUMN.tableName, params.get("tableName_like") + "%")
                .like(StringUtils.hasText(po.getTableRemark()), SysGenTable.COLUMN.tableRemark, po.getTableRemark() + "%");
        where.orderBy(page);
        return commonService.page(SysGenTable.class, where, page);
    }

    @SaCheckPermission("sysGenTable:view")
    @GetMapping
    public Result<SysGenTable> detail(@RequestParam Long id){
        return Result.success( sysGenTableService.detail( id) );
    }

    @AdminLog("新增表单生成记录")
    @SaCheckPermission("sysGenTable:add")
    @PostMapping
    public Result<Void> add(@RequestBody SysGenTable sysGenTable){
        sysGenTableService.saveGenFormRecord(sysGenTable);
        return Result.success();
    }

}
