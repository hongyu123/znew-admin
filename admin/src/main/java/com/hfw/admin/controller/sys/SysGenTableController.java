package com.hfw.admin.controller.sys;

import com.hfw.basesystem.dto.SysGenTableDTO;
import com.hfw.basesystem.entity.SysGenTable;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.service.SysGenTableService;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 表单生成记录控制器
 * @author  
 * @date 2023-01-04
 */
@RestController
@RequestMapping("/sysGenTable")
public class SysGenTableController {

    @Resource
    private CommonService<SysGenTable> commonService;
    @Resource
    private SysGenTableService sysGenTableService;

    @GetMapping
    public PageResult page(SysGenTableDTO dto){
        return sysGenTableService.page(dto);
    }

    @GetMapping("/{id}")
    public ApiResult detail(@PathVariable("id") Long id){
        return ApiResult.data( sysGenTableService.detail(id) );
    }

    //保存Java代码生成
    @PostMapping
    public ApiResult add(@RequestBody SysGenTable sysGenTable){
        sysGenTableService.saveGenFormRecord(sysGenTable);
        return ApiResult.success();
    }

    //@PostMapping("/edit")
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) SysGenTable sysGenTable){
        commonService.edit(sysGenTable);
        return ApiResult.success();
    }

    //@PostMapping("/del")
    public ApiResult del(@RequestBody SysGenTable sysGenTable){
        commonService.del(SysGenTable.class, sysGenTable.getId());
        return ApiResult.success();
    }

    //@PostMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        commonService.dels(SysGenTable.class, ids);
        return ApiResult.success();
    }

}