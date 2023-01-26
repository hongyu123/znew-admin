package com.hfw.admin.controller.sys;

import com.hfw.admin.log.AdminLog;
import com.hfw.basesystem.dto.AppArticleDTO;
import com.hfw.basesystem.entity.AppArticle;
import com.hfw.basesystem.service.AppArticleService;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * app文章控制器
 * @author farkle
 * @date 2022-12-20
 */
@RestController
@RequestMapping("/appArticle")
public class AppArticleController {

    @Resource
    private CommonService<AppArticle> commonService;
    @Resource
    private AppArticleService appArticleService;

    @GetMapping
    public PageResult page(AppArticleDTO dto){
        return appArticleService.page(dto);
    }

    @GetMapping("/{id}")
    public ApiResult detail(@PathVariable("id") Long id){
        return ApiResult.data( appArticleService.detail(id) );
    }

    @AdminLog("新增系统文章")
    @PostMapping
    public ApiResult add(@RequestBody @Validated(ValidGroup.Add.class) AppArticle appArticle){
        appArticleService.save(appArticle);
        return ApiResult.success();
    }

    @AdminLog("编辑系统文章")
    @PutMapping
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) AppArticle appArticle){
        appArticleService.edit(appArticle);
        return ApiResult.success();
    }

    @AdminLog("删除系统文章")
    @DeleteMapping("/{id}")
    public ApiResult del(@PathVariable("id") Long id){
        appArticleService.del(id);
        return ApiResult.success();
    }

    @AdminLog("批量删除系统文章")
    //@PostMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        commonService.dels(AppArticle.class, ids);
        return ApiResult.success();
    }

}