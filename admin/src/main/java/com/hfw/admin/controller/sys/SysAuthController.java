package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hfw.admin.log.AdminLog;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysAuth;
import com.hfw.service.sys.sysAuth.SysAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统权限控制器
 * @author farkle
 * @date 2022-12-14
 */
@RestController
@RequestMapping("/sysAuth")
public class SysAuthController {
    @Autowired
    private SysAuthService sysAuthService;

    @SaCheckPermission("sysAuth:view")
    @GetMapping
    public Result<SysAuth> detail(@RequestParam Long id){
        return Result.success( sysAuthService.detail(id) );
    }

    @AdminLog("新增系统权限")
    @SaCheckPermission("sysAuth:add")
    @PostMapping
    public Result<Void> add(@RequestBody SysAuth sysAuth){
        return Result.result(sysAuthService.add(sysAuth));
    }

    @AdminLog("编辑系统权限")
    @SaCheckPermission("sysAuth:edit")
    @PutMapping
    public Result<Void> edit(@RequestBody SysAuth sysAuth){
        return sysAuthService.edit(sysAuth);
    }

    @AdminLog("删除系统权限")
    @SaCheckPermission("sysAuth:del")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        return sysAuthService.del(id);
    }

    //查询树形结构数据
    @GetMapping("/tree")
    @SaCheckPermission("sysAuth:page")
    public Result<List<SysAuth>> tree(EnableState state){
        return Result.success( sysAuthService.currentUserAuthsWithOwn(state) );
    }

}
