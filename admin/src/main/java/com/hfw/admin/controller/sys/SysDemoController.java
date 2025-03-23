package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.enums.sys.SortByWay;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysDemo;
import com.hfw.model.validation.ValidGroup;
import com.hfw.service.sys.sysDemo.SysDemoDTO;
import com.hfw.service.sys.sysDemo.SysDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统示例表控制器
 * @author farkle
 * @date 2023-01-26
 */
@RestController
@RequestMapping("/sysDemo")
public class SysDemoController {
    @Autowired
    private SysDemoService sysDemoService;

    @GetMapping("/page")
    @SaCheckPermission("sysDemo:page")
    public PageResult<SysDemo> page(Page<SysDemo> page, SysDemo po) {
        page.sort(SortByWay.desc,"id");
        return PageResult.of(sysDemoService.page(page, po));
    }

    @GetMapping
    @SaCheckPermission("sysDemo:view")
    public Result<SysDemoDTO> detail(@RequestParam Long id){
        return Result.success( sysDemoService.detail( id) );
    }

    @AdminLog("新增系统示例表")
    @SaCheckPermission("sysDemo:add")
    @PostMapping
    public Result<Void> add(@RequestBody @Validated(ValidGroup.Add.class) SysDemoDTO sysDemo){
        return Result.result(sysDemoService.add(sysDemo));
    }

    @AdminLog("编辑系统示例表")
    @SaCheckPermission("sysDemo:edit")
    @PutMapping
    public Result<Void> edit(@RequestBody @Validated(ValidGroup.Update.class) SysDemoDTO sysDemo){
        return Result.result(sysDemoService.edit(sysDemo));
    }

    @AdminLog("删除系统示例表")
    @SaCheckPermission("sysDemo:del")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        return Result.result(sysDemoService.del(id));
    }

    @AdminLog("批量删除系统示例表")
    @SaCheckPermission("sysDemo:del")
    @DeleteMapping("/dels")
    public Result<Void> dels(@RequestBody List<Long> ids){
        return Result.result(sysDemoService.dels(ids));
    }

}