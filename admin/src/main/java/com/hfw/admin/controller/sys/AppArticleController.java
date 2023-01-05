package com.hfw.admin.controller.sys;

import com.hfw.basesystem.dto.AppArticleDTO;
import com.hfw.basesystem.entity.AppArticle;
import com.hfw.basesystem.service.AppArticleService;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.basesystem.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * app文章控制器
 * @author zyh
 * @date 2022-12-20
 */
@RestController
@RequestMapping("/appArticle")
public class AppArticleController {

    @Autowired
    private CommonService<AppArticle> commonService;
    @Autowired
    private AppArticleService appArticleService;

    @GetMapping("/page")
    public PageResult page(AppArticleDTO dto){
        return appArticleService.page(dto);
    }

    @GetMapping("/detail")
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( appArticleService.detail(id) );
    }

    @PostMapping("/save")
    public ApiResult save(@RequestBody @Validated(ValidGroup.Add.class) AppArticle appArticle){
        appArticleService.save(appArticle);
        return ApiResult.success();
    }

    @PostMapping("/edit")
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) AppArticle appArticle){
        appArticleService.edit(appArticle);
        return ApiResult.success();
    }

    @PostMapping("/del")
    public ApiResult del(@RequestBody @Validated(ValidGroup.Del.class) AppArticle appArticle){
        appArticleService.del(appArticle.getId());
        return ApiResult.success();
    }

    @PostMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        commonService.dels(AppArticle.class, ids);
        return ApiResult.success();
    }

}