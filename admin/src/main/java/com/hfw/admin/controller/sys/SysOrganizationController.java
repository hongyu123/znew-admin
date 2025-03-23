package com.hfw.admin.controller.sys;

import com.hfw.admin.log.AdminLog;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysOrganization;
import com.hfw.service.sys.sysOrganization.SysOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织机构Controller
 * @author  farkle
 * @date 2025-03-16
 */
@RestController
@RequestMapping("/sysOrganization")
public class SysOrganizationController {
    @Autowired
    private SysOrganizationService sysOrganizationService;

    /*@GetMapping("/page")
    public PageResult<SysOrganization> page(Page<SysOrganization> page, SysOrganization po) {
        return PageResult.of(sysOrganizationService.page(page, po));
    }*/

    @GetMapping
    public Result<SysOrganization> detail(@RequestParam Long id){
        return Result.success( sysOrganizationService.detail( id) );
    }

    @AdminLog("新增组织机构")
    @PostMapping
    public Result<Void> add(@RequestBody SysOrganization sysOrganization){
        return sysOrganizationService.add(sysOrganization);
    }

    @AdminLog("编辑组织机构")
    @PutMapping
    public Result<Void> edit(@RequestBody SysOrganization  sysOrganization){
        return sysOrganizationService.edit(sysOrganization);
    }

    @AdminLog("删除组织机构")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        return sysOrganizationService.del(id);
    }

    @GetMapping("/tree")
    public Result<List<SysOrganization>> tree(@RequestParam(required = false) EnableState state){
        return Result.success( sysOrganizationService.selfOrgTree(state) );
    }

}
