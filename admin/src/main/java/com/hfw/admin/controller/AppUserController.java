package com.hfw.admin.controller;

import com.hfw.admin.dto.AppUserDTO;
import com.hfw.admin.log.AdminLog;
import com.hfw.admin.service.AppUserService;
import com.hfw.basesystem.config.RedisUtil;
import com.hfw.basesystem.entity.AppUser;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.PageResult;
import com.hfw.common.enums.EnableState;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * app用户控制器
 * @author farkle
 * @date 2022-12-11
 */
@RestController
@RequestMapping("/appUser")
public class AppUserController {

    @Resource
    private CommonService<AppUser> commonService;
    @Resource
    private AppUserService appUserService;

    @GetMapping("/page")
    public PageResult page(AppUserDTO dto){
        return appUserService.page(dto);
    }

    @GetMapping
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( commonService.getById(AppUser.class, id) );
    }

    //@PostMapping("/add")
    public ApiResult add(@RequestBody @Validated(ValidGroup.Add.class) AppUser appUser){
        commonService.save(appUser);
        return ApiResult.success();
    }

    //@PostMapping("/edit")
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) AppUser appUser){
        commonService.edit(appUser);
        return ApiResult.success();
    }

   // @PostMapping("/del")
    public ApiResult del(@RequestBody AppUser appUser){
        commonService.del(AppUser.class, appUser.getId());
        return ApiResult.success();
    }

    //@PostMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        commonService.dels(AppUser.class, ids);
        return ApiResult.success();
    }

    @Resource
    private RedisUtil redisUtil;

    @AdminLog("APP用户启用/禁用")
    @PutMapping("/state")
    public ApiResult state(@RequestBody @Validated(ValidGroup.Update.class) AppUser appUser){
        if(appUser.getEnableState()==null){
            return ApiResult.error("状态不能为空!");
        }
        if(appUser.getEnableState() == EnableState.Disable){
            redisUtil.del("appuser:"+ appUser.getId());
        }
        commonService.edit(new AppUser().setId(appUser.getId()).setEnableState(appUser.getEnableState()).setComment(appUser.getComment()));
        return ApiResult.success();
    }

}