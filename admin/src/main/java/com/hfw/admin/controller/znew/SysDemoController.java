//package com.hfw.admin.controller.znew;
//
//import cn.dev33.satoken.annotation.SaCheckPermission;
//import com.hfw.admin.log.AdminLog;
//import com.hfw.model.entity.Page;
//import com.hfw.model.entity.PageResult;
//import com.hfw.model.jackson.Result;
//import com.hfw.model.mybatis.Where;
//import com.hfw.model.mybatis.anno.Sort;
//import com.hfw.service.component.CommonService;
//import com.hfw.model.po.znew.SysDemo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 系统示例表Controller
// * @author  farkle
// * @date 2026-02-17
// */
//@RestController
//@RequestMapping("/sysDemo")
//public class SysDemoController {
//    @Autowired
//    private CommonService commonService;
//
//    @SaCheckPermission("sysDemo:page")
//    @GetMapping("/page")
//    public PageResult<SysDemo> page(Page<SysDemo> page, SysDemo po) {
//        Map<String, String> params = page.getParams();
//        Where<SysDemo> where = Where.<SysDemo>where()
//                .eq(StringUtils.hasText(po.getName()), SysDemo.COLUMN.name, po.getName())
//                .like(StringUtils.hasText(po.getPhone()), SysDemo.COLUMN.phone, po.getPhone())
//                .eq(po.getGender() != null, SysDemo.COLUMN.gender, po.getGender())
//                .eq(po.getState() != null, SysDemo.COLUMN.state, po.getState())
//                .eq(po.getBirth() != null, SysDemo.COLUMN.birth, po.getBirth())
//                .ge(StringUtils.hasText(params.get("registTime_gt")), SysDemo.COLUMN.birth, params.get("registTime_gt"))
//                .le(StringUtils.hasText(params.get("registTime_lt")), SysDemo.COLUMN.birth, params.get("registTime_lt"));
//        if(!where.orderBy(page)){
//            where.orderBy(SysDemo.COLUMN.id, Sort.DESC);
//        }
//        return commonService.page(SysDemo.class, where, page);
//    }
//
//    @SaCheckPermission("sysDemo:view")
//    @GetMapping
//    public Result<SysDemo> detail(@RequestParam Long id){
//        SysDemo po = commonService.selectByPk(SysDemo.class, id);
//        return Result.success(po);
//    }
//
//    @AdminLog("新增系统示例表")
//    @SaCheckPermission("sysDemo:add")
//    @PostMapping
//    public Result<Void> add(@RequestBody SysDemo sysDemo){
//        return Result.result(commonService.insert(sysDemo));
//    }
//
//    @AdminLog("编辑系统示例表")
//    @SaCheckPermission("sysDemo:edit")
//    @PutMapping
//    public Result<Void> edit(@RequestBody SysDemo sysDemo){
//        return Result.result(commonService.updateByPk(sysDemo));
//    }
//
//    @AdminLog("删除系统示例表")
//    @SaCheckPermission("sysDemo:del")
//    @DeleteMapping
//    public Result<Void> del(@RequestParam Long id){
//        int res = commonService.deleteByPk(SysDemo.class, id);
//        return Result.result(res);
//    }
//
//    @AdminLog("批量删除系统示例表")
//    @SaCheckPermission("sysDemo:del")
//    @DeleteMapping("/dels")
//    public Result<Void> dels(@RequestBody List<Long> ids){
//        int res = commonService.deleteByPks(SysDemo.class, ids);
//        return Result.result(res);
//    }
//
//}
