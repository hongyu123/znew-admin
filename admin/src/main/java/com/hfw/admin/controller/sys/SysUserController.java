package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.fastjson2.JSONObject;
import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysUser;
import com.hfw.model.validation.ValidGroup;
import com.hfw.service.sys.sysUser.SysUserDTO;
import com.hfw.service.sys.sysUser.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户控制器
 * @author farkle
 * @date 2022-12-14
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @SaCheckPermission("sysUser:page")
    @GetMapping("/page")
    public PageResult<SysUser> page(Page<SysUser> page, SysUser po) {
        return PageResult.of(sysUserService.page(page, po));
    }

    @SaCheckPermission("sysUser:view")
    @GetMapping
    public Result<SysUserDTO> detail(@RequestParam Long id){
        return Result.success( sysUserService.detail( id) );
    }

    @AdminLog("新增系统用户")
    @SaCheckPermission("sysUser:add")
    @PostMapping
    public Result<Void> save(@RequestBody @Validated(ValidGroup.Add.class) SysUserDTO dto){
        return sysUserService.save(dto);
    }

    @AdminLog("编辑系统用户")
    @SaCheckPermission("sysUser:edit")
    @PutMapping
    public Result<Void> edit(@RequestBody @Validated(ValidGroup.Update.class) SysUserDTO dto){
        return sysUserService.edit(dto);
    }

    @AdminLog("删除系统用户")
    @SaCheckPermission("sysUser:del")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        return sysUserService.del(id);
    }

    /*@GetMapping("/current")
    public ApiResult currentUserInfo(){
        return this.detail( LoginUser.getLoginUser().getId() );
    }*/

    @AdminLog("修改密码")
    @PostMapping("/changePassword")
    public Result<Void> changePassword(@RequestBody JSONObject info){
        return sysUserService.changePassword(info.getString("oldPassword"), info.getString("password"));
    }

    @AdminLog("重置密码")
    @SaCheckPermission("sysUser:pwd")
    @PostMapping("/resetPassword")
    public Result<Void> resetPassword(@RequestBody JSONObject info){
        Long id = info.getLong("id");
        if(id==null){
            return Result.error("id不能为空");
        }
        return Result.result(sysUserService.resetPassword(id));
    }

    @GetMapping("/userInfo")
    public Result<SysUser> userInfo(){
        return Result.success(sysUserService.userInfo());
    }
    @AdminLog("编辑用户信息")
    @PostMapping("/editInfo")
    public Result<Void> editInfo(@RequestBody SysUser sysUser){
        return Result.result(sysUserService.editInfo(sysUser));
    }

}
