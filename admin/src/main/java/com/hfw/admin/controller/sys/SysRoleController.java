package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysRole;
import com.hfw.model.po.sys.SysUser;
import com.hfw.model.po.sys.SysUserRole;
import com.hfw.service.sys.sysRole.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统角色控制器
 * @author farkle
 * @date 2022-12-14
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @SaCheckPermission("sysRole:page")
    @GetMapping("/page")
    public PageResult<SysRole> page(Page<SysRole> page, SysRole po) {
        return PageResult.of(sysRoleService.page(page, po));
    }

    @SaCheckPermission("sysRole:view")
    @GetMapping
    public Result<SysRole> detail(@RequestParam Long id){
        return Result.success( sysRoleService.detail( id) );
    }

    @AdminLog("新增系统角色")
    @SaCheckPermission("sysRole:add")
    @PostMapping
    public Result<Void> add(@RequestBody SysRole sysRole){
        sysRoleService.add(sysRole);
        return Result.success();
    }

    @AdminLog("编辑系统角色")
    @SaCheckPermission("sysRole:edit")
    @PutMapping
    public Result<Void> edit(@RequestBody SysRole sysRole){
        return sysRoleService.edit(sysRole);
    }

    @AdminLog("删除系统角色")
    @SaCheckPermission("sysRole:del")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        return sysRoleService.del(id);
    }

    @GetMapping("/currentUserRolesWithOwn")
    public Result<List<SysRole>> currentUserRolesWithOwn(){
        return Result.success( sysRoleService.currentUserRolesWithOwn() );
    }

    //查询角色下的用户
    @GetMapping("/users")
    public PageResult<SysUser> users(Page<SysUser> page, @RequestParam Long roleId){
        return PageResult.of( sysRoleService.users(page, roleId) );
    }

    @AdminLog("角色授权用户")
    @SaCheckPermission("sysRole:role")
    @PostMapping("/users")
    public Result<Void> allocateUsers(@RequestBody SysUserRole sysUserRole){
        sysRoleService.allocateUsers(sysUserRole);
        return Result.success();
    }

    @AdminLog("角色取消授权用户")
    @SaCheckPermission("sysRole:role")
    @DeleteMapping("/users")
    public Result<Void> cancelUsers(@RequestBody SysUserRole sysUserRole){
        sysRoleService.cancelUsers(sysUserRole);
        return Result.success();
    }

}
