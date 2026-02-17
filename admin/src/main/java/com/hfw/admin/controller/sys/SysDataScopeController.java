package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import com.hfw.model.mybatis.Where;
import com.hfw.model.po.sys.SysDataScope;
import com.hfw.service.component.CommonService;
import com.hfw.service.sys.sysDataScope.SysDataScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据权限表Controller
 * @author  farkle
 * @date 2025-03-30
 */
@RestController
@RequestMapping("/sysDataScope")
public class SysDataScopeController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private SysDataScopeService sysDataScopeService;

    @SaCheckPermission("sysDataScope:page")
    @GetMapping("/page")
    public PageResult<SysDataScope> page(Page<SysDataScope> page, SysDataScope po) {
        Where<SysDataScope> where = Where.<SysDataScope>where()
                .eq(po.getConfigType() != null, SysDataScope.COLUMN.configType, po.getConfigType())
                .like(StringUtils.hasText(po.getConfigName()), SysDataScope.COLUMN.configName, po.getConfigName() + "%")
                .like(StringUtils.hasText(po.getDataKey()), SysDataScope.COLUMN.dataKey, po.getDataKey() + "%");
        return commonService.page(SysDataScope.class, where, page);
    }

    @SaCheckPermission("sysDataScope:view")
    @GetMapping
    public Result<SysDataScope> detail(@RequestParam Long id){
        return Result.success( sysDataScopeService.detail( id) );
    }

    @AdminLog("新增数据权限表")
    @SaCheckPermission("sysDataScope:add")
    @PostMapping
    public Result<Void> add(@RequestBody SysDataScope sysDataScope){
        return Result.result(sysDataScopeService.add(sysDataScope));
    }

    @AdminLog("编辑数据权限表")
    @SaCheckPermission("sysDataScope:edit")
    @PutMapping
    public Result<Void> edit(@RequestBody SysDataScope sysDataScope){
        return Result.result(sysDataScopeService.edit(sysDataScope));
    }

    @AdminLog("删除数据权限表")
    @SaCheckPermission("sysDataScope:del")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        return Result.result(sysDataScopeService.del(id));
    }

    @AdminLog("批量删除数据权限表")
    @SaCheckPermission("sysDataScope:del")
    @DeleteMapping("/dels")
    public Result<Void> dels(@RequestBody List<Long> ids){
        return Result.result(sysDataScopeService.dels(ids));
    }

}
