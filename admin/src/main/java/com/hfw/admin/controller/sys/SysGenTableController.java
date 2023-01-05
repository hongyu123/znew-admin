package com.hfw.admin.controller.sys;

import com.hfw.common.entity.PageResult;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.service.SysGenTableService;
import com.hfw.basesystem.dto.SysGenTableDTO;
import com.hfw.basesystem.entity.SysGenTable;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CommonService<SysGenTable> commonService;
    @Autowired
    private SysGenTableService sysGenTableService;

    @GetMapping("/page")
    public PageResult page(SysGenTableDTO dto){
        return sysGenTableService.page(dto);
    }

    @GetMapping("/detail")
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( sysGenTableService.detail(id) );
    }

    //@PostMapping("/add")
    public ApiResult add(@RequestBody @Validated(ValidGroup.Add.class) SysGenTable sysGenTable){
        commonService.add(sysGenTable);
        return ApiResult.success();
    }

    //@PostMapping("/edit")
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) SysGenTable sysGenTable){
        commonService.edit(sysGenTable);
        return ApiResult.success();
    }

    //@PostMapping("/del")
    public ApiResult del(@RequestBody @Validated(ValidGroup.Del.class) SysGenTable sysGenTable){
        commonService.del(SysGenTable.class, sysGenTable.getId());
        return ApiResult.success();
    }

    //@PostMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        commonService.dels(SysGenTable.class, ids);
        return ApiResult.success();
    }

}