package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysDictionary;
import com.hfw.service.sys.sysDictionary.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典表Controller
 * @author  farkle
 * @date 2025-03-29
 */
@RestController
@RequestMapping("/sysDictionary")
public class SysDictionaryController {
    @Autowired
    private SysDictionaryService sysDictionaryService;

    @SaCheckPermission("sysDict:page")
    @GetMapping("/page")
    public PageResult<SysDictionary> page(Page<SysDictionary> page, SysDictionary po) {
        return PageResult.of(sysDictionaryService.page(page, po));
    }

    @SaCheckPermission("sysDict:page")
    @GetMapping("/children")
    public Result<List<SysDictionary>> children(@RequestParam Long id){
        SysDictionary po = new SysDictionary();
        po.setPid(id);
        Page<SysDictionary> pageResult = sysDictionaryService.page(Page.nonePage(), po);
        return Result.success( pageResult.getList() );
    }

    @SaCheckPermission("sysDict:view")
    @GetMapping
    public Result<SysDictionary> detail(@RequestParam Long id){
        return Result.success( sysDictionaryService.detail( id) );
    }

    @AdminLog("新增字典表")
    @SaCheckPermission("sysDict:add")
    @PostMapping
    public Result<Void> add(@RequestBody SysDictionary sysDictionary){
        return Result.result(sysDictionaryService.add(sysDictionary));
    }

    @AdminLog("编辑字典表")
    @SaCheckPermission("sysDict:edit")
    @PutMapping
    public Result<Void> edit(@RequestBody SysDictionary sysDictionary){
        return Result.result(sysDictionaryService.edit(sysDictionary));
    }

    @AdminLog("删除字典表")
    @SaCheckPermission("sysDict:del")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        return sysDictionaryService.del(id);
    }

    @AdminLog("批量删除字典表")
    @SaCheckPermission("sysDict:del")
    @DeleteMapping("/dels")
    public Result<Void> dels(@RequestBody List<Long> ids){
        return Result.result(sysDictionaryService.dels(ids));
    }

}
