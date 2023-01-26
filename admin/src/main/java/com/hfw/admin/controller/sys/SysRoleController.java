package com.hfw.admin.controller.sys;

import com.hfw.admin.log.AdminLog;
import com.hfw.admin.security.LoginUser;
import com.hfw.basesystem.dto.SysRoleDTO;
import com.hfw.basesystem.dto.SysUserRoleDTO;
import com.hfw.basesystem.entity.SysRole;
import com.hfw.basesystem.service.SysRoleService;
import com.hfw.common.entity.PageResult;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.basesystem.service.CommonService;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统角色控制器
 * @author farkle
 * @date 2022-12-14
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Resource
    private CommonService<SysRole> commonService;
    @Resource
    private SysRoleService sysRoleService;

    @GetMapping
    public PageResult page(SysRoleDTO dto){
        return sysRoleService.page(dto);
    }

    @GetMapping("/{id}")
    public ApiResult detail(@PathVariable("id") Long id){
        return ApiResult.data( sysRoleService.detail(id) );
    }

    @AdminLog("新增系统角色")
    @PostMapping
    public ApiResult save(@RequestBody @Validated(ValidGroup.Add.class) SysRoleDTO dto){
        dto.setCreator(LoginUser.getLoginUser().getUsername());
        dto.setCreateTime(LocalDateTime.now());
        sysRoleService.save(dto);
        return ApiResult.success();
    }

    @AdminLog("编辑系统角色")
    @PutMapping
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) SysRoleDTO dto){
        dto.setUpdator(LoginUser.getLoginUser().getUsername());
        dto.setUpdateTime(LocalDateTime.now());
        sysRoleService.edit(dto);
        return ApiResult.success();
    }

    @AdminLog("删除系统角色")
    @DeleteMapping("/{id}")
    public ApiResult del(@PathVariable Long id){
        sysRoleService.del(id);
        return ApiResult.success();
    }

    //@PostMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        commonService.dels(SysRole.class, ids);
        return ApiResult.success();
    }

    @GetMapping("/list")
    public ApiResult list(SysRole sysRole){
        return ApiResult.list( commonService.list(sysRole) );
    }

    //查询角色下的用户
    @GetMapping("/users")
    public PageResult users(SysUserRoleDTO dto){
        return sysRoleService.users(dto);
    }

    @AdminLog("角色授权用户")
    @PostMapping("/users")
    public ApiResult addUsers(@RequestBody @Validated SysUserRoleDTO dto){
        sysRoleService.addUsers(dto);
        return ApiResult.success();
    }

    @AdminLog("角色取消授权用户")
    @DeleteMapping("/users")
    public ApiResult delUsers(@RequestBody @Validated SysUserRoleDTO dto){
        sysRoleService.delUsers(dto);
        return ApiResult.success();
    }
}