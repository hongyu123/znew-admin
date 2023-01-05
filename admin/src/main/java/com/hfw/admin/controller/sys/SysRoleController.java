package com.hfw.admin.controller.sys;

import com.hfw.admin.security.LoginUser;
import com.hfw.basesystem.dto.SysRoleDTO;
import com.hfw.basesystem.dto.SysUserRoleDTO;
import com.hfw.basesystem.entity.SysRole;
import com.hfw.basesystem.service.SysRoleService;
import com.hfw.common.entity.PageResult;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.basesystem.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统角色控制器
 * @author zyh
 * @date 2022-12-14
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private CommonService<SysRole> commonService;
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/page")
    public PageResult page(SysRoleDTO dto){
        return sysRoleService.page(dto);
    }

    @GetMapping("/detail")
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( sysRoleService.detail(id) );
    }

    @PostMapping("/save")
    public ApiResult save(@RequestBody @Validated(ValidGroup.Add.class) SysRoleDTO dto){
        dto.setCreator(LoginUser.getLoginUser().getUsername());
        dto.setCreateTime(LocalDateTime.now());
        sysRoleService.save(dto);
        return ApiResult.success();
    }

    @PostMapping("/edit")
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) SysRoleDTO dto){
        dto.setUpdator(LoginUser.getLoginUser().getUsername());
        dto.setUpdateTime(LocalDateTime.now());
        sysRoleService.edit(dto);
        return ApiResult.success();
    }

    @PostMapping("/del")
    public ApiResult del(@RequestBody @Validated(ValidGroup.Del.class) SysRole sysRole){
        sysRoleService.del(sysRole.getId());
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
    //角色授权用户
    @PostMapping("/addUsers")
    public ApiResult addUsers(@RequestBody @Validated SysUserRoleDTO dto){
        sysRoleService.addUsers(dto);
        return ApiResult.success();
    }
    //角色取消授权用户
    @PostMapping("/delUsers")
    public ApiResult delUsers(@RequestBody @Validated SysUserRoleDTO dto){
        sysRoleService.delUsers(dto);
        return ApiResult.success();
    }
}