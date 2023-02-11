package com.hfw.admin.controller.sys;

import com.hfw.admin.log.AdminLog;
import com.hfw.admin.security.LoginUser;
import com.hfw.basesystem.dto.SysAuthDTO;
import com.hfw.basesystem.entity.SysAuth;
import com.hfw.basesystem.service.CommonService;
import com.hfw.basesystem.service.SysAuthService;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统权限控制器
 * @author farkle
 * @date 2022-12-14
 */
@RestController
@RequestMapping("/sysAuth")
public class SysAuthController {

    @Resource
    private CommonService<SysAuth> commonService;
    @Resource
    private SysAuthService sysAuthService;

    @GetMapping("/page")
    public PageResult page(SysAuthDTO dto){
        return sysAuthService.page(dto);
    }

    @GetMapping
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( commonService.getById(SysAuth.class, id) );
    }

    @AdminLog("新增系统权限")
    @PostMapping
    public ApiResult save(@RequestBody @Validated(ValidGroup.Add.class) SysAuth sysAuth){
        sysAuth.saveFilter();
        sysAuth.setCreator(LoginUser.getLoginUser().getUsername());
        sysAuth.setCreateTime(LocalDateTime.now());
        commonService.save(sysAuth);
        return ApiResult.success();
    }

    @AdminLog("编辑系统权限")
    @PutMapping
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) SysAuth sysAuth){
        if(sysAuth.getId().equals(sysAuth.getParentId())){
            return ApiResult.error("不能设置父节点为自己");
        }
        sysAuth.updateFilter();
        sysAuth.setUpdator(LoginUser.getLoginUser().getUsername());
        sysAuth.setUpdateTime(LocalDateTime.now());
        commonService.edit(sysAuth);
        return ApiResult.success();
    }

    @AdminLog("删除系统权限")
    @DeleteMapping
    public ApiResult del(@RequestParam Long id){
        Long cnt = commonService.count(new SysAuth().setParentId(id));
        if(cnt>0){
            return ApiResult.error("节点下有子节点,无法删除!");
        }
        commonService.del(SysAuth.class, id);
        return ApiResult.success();
    }

    //@PostMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        commonService.dels(SysAuth.class, ids);
        return ApiResult.success();
    }

    //查询子节点
    @GetMapping("/children")
    public ApiResult children(@RequestParam(defaultValue = "0") Long parentId){
        return ApiResult.list( sysAuthService.children(parentId) );
    }

    //查询树形结构数据
    @GetMapping("/tree")
    public ApiResult tree(SysAuthDTO dto){
        return ApiResult.list( sysAuthService.treeList(dto) );
    }

}