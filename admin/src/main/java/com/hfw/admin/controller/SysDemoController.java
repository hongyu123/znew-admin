package com.hfw.admin.controller;

import com.hfw.admin.log.AdminLog;
import com.hfw.common.entity.PageResult;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.basesystem.service.CommonService;
import com.hfw.admin.service.SysDemoService;
import com.hfw.admin.dto.SysDemoDTO;
import com.hfw.model.entity.SysDemo;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 系统示例表控制器
 * @author
 * @date 2023-01-26
 */
@RestController
@RequestMapping("/sysDemo")
public class SysDemoController {
    @Resource
    private SysDemoService sysDemoService;

    @GetMapping("/page")
    public PageResult page(SysDemoDTO dto){
        return sysDemoService.page(dto);
    }

    @GetMapping
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( sysDemoService.detail( id) );
    }

    @AdminLog("新增系统示例表")
    @PostMapping
    public ApiResult add(@RequestBody @Validated(ValidGroup.Add.class) SysDemo sysDemo){
        sysDemoService.add(sysDemo);
        return ApiResult.success();
    }

    @AdminLog("编辑系统示例表")
    @PutMapping
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) SysDemo sysDemo){
        sysDemoService.edit(sysDemo);
        return ApiResult.success();
    }

    @AdminLog("删除系统示例表")
    @DeleteMapping
    public ApiResult del(@RequestParam Long id){
        sysDemoService.del(id);
        return ApiResult.success();
    }

    @AdminLog("批量删除系统示例表")
    @DeleteMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        ids.forEach( id-> sysDemoService.del(id) );
        return ApiResult.success();
    }

}