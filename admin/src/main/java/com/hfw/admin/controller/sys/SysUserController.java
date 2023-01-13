package com.hfw.admin.controller.sys;

import com.hfw.admin.log.AdminLog;
import com.hfw.admin.security.LoginUser;
import com.hfw.basesystem.dto.SysUserDTO;
import com.hfw.basesystem.entity.SysUser;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.service.SysUserService;
import com.hfw.common.entity.PageResult;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.common.util.ValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户控制器
 * @author farkle
 * @date 2022-12-14
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private CommonService<SysUser> commonService;
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/page")
    public PageResult page(SysUserDTO dto){
        return sysUserService.page(dto);
    }

    @GetMapping("/detail")
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( sysUserService.detail(id) );
    }
    @GetMapping("/current")
    public ApiResult currentUserInfo(){
        return this.detail( LoginUser.getLoginUser().getId() );
    }

    @AdminLog("新增系统用户")
    @PostMapping("/save")
    public ApiResult save(@RequestBody @Validated(ValidGroup.Add.class) SysUserDTO dto){
        dto.setCreator(LoginUser.getLoginUser().getUsername());
        dto.setCreateTime(LocalDateTime.now());
        sysUserService.save(dto);
        return ApiResult.success();
    }

    @AdminLog("编辑系统用户")
    @PostMapping("/edit")
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) SysUserDTO dto){
        LoginUser loginUser = LoginUser.getLoginUser();
        dto.setCurrentUserId(loginUser.getId());
        dto.setUpdator(loginUser.getUsername());
        dto.setUpdateTime(LocalDateTime.now());
        sysUserService.edit(dto);
        return ApiResult.success();
    }

    @AdminLog("修改密码")
    @PostMapping("/changePassword")
    public ApiResult changePassword(@RequestBody SysUserDTO dto){
        if(!ValidUtil.validPassword(dto.getPassword()) ){
            return ApiResult.error("密码格式错误!");
        }
        dto.setId(LoginUser.getLoginUser().getId());
        sysUserService.changePassword(dto);
        return ApiResult.success();
    }

    @AdminLog("重置密码")
    @PostMapping("/resetPassword")
    public ApiResult resetPassword(@RequestBody SysUser sysUser){
        sysUserService.resetPassword(sysUser);
        return ApiResult.success();
    }

    @AdminLog("删除系统用户")
    @PostMapping("/del")
    public ApiResult del(@RequestBody @Validated(ValidGroup.Del.class) SysUserDTO dto){
        LoginUser loginUser = LoginUser.getLoginUser();
        dto.setCurrentUserId(loginUser.getId());
        sysUserService.del(dto);
        return ApiResult.success();
    }

    //@PostMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        commonService.dels(SysUser.class, ids);
        return ApiResult.success();
    }

}