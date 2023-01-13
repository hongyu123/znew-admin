package com.hfw.admin.controller;

import com.hfw.admin.dto.SysDemoDTO;
import com.hfw.admin.service.SysDemoService;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.model.entity.SysDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统示例表控制器
 * @author  
 * @date 2023-01-04
 */
@RestController
@RequestMapping("/sysDemo")
public class SysDemoController {
    @Autowired
    private SysDemoService sysDemoService;

    @GetMapping("/page")
    public PageResult page(SysDemoDTO dto){
        return sysDemoService.page(dto);
    }

    @GetMapping("/detail")
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( sysDemoService.detail( id) );
    }

    @PostMapping("/add")
    public ApiResult add(@RequestBody @Validated(ValidGroup.Add.class) SysDemo sysDemo){
        sysDemoService.add(sysDemo);
        return ApiResult.success();
    }

    @PostMapping("/edit")
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) SysDemo sysDemo){
        sysDemoService.edit(sysDemo);
        return ApiResult.success();
    }

    @PostMapping("/del")
    public ApiResult del(@RequestBody @Validated(ValidGroup.Del.class) SysDemo sysDemo){
        sysDemoService.del(sysDemo.getId());
        return ApiResult.success();
    }

    @PostMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        ids.forEach( id-> sysDemoService.del(id) );
        return ApiResult.success();
    }

}