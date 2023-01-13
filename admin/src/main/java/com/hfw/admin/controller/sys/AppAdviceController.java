package com.hfw.admin.controller.sys;

import com.hfw.admin.log.AdminLog;
import com.hfw.basesystem.dto.AppAdviceDTO;
import com.hfw.basesystem.entity.AppAdvice;
import com.hfw.basesystem.service.AppAdviceService;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.ParamMap;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * app建议反馈控制器
 * @author farkle
 * @date 2022-12-20
 */
@RestController
@RequestMapping("/appAdvice")
public class AppAdviceController {

    @Autowired
    private CommonService<AppAdvice> commonService;
    @Autowired
    private AppAdviceService appAdviceService;

    @GetMapping("/page")
    public PageResult page(AppAdviceDTO dto){
        return appAdviceService.page(dto);
    }

    @GetMapping("/detail")
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( commonService.detail(AppAdvice.class, id) );
    }
    //未读数量
    @GetMapping("/unreadCount")
    public ApiResult unreadCount(){
        Long count = commonService.count(new AppAdvice().setReadFlag(0));
        return ApiResult.data(ParamMap.create().put("count", count));
    }

    @AdminLog("建议已读")
    @PostMapping("/read")
    public ApiResult read(@RequestBody @Validated(ValidGroup.Update.class) AppAdvice appAdvice){
        commonService.edit(new AppAdvice().setId(appAdvice.getId()).setReadFlag(1));
        return ApiResult.success();
    }

    @AdminLog("建议批量已读")
    @PostMapping("/reads")
    public ApiResult reads(@RequestBody List<Long> ids){
        appAdviceService.batchRead(ids);
        return ApiResult.success();
    }

}