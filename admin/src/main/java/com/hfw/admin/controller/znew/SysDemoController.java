//package com.hfw.admin.controller.znew;
//
//import com.hfw.admin.log.AdminLog;
//import com.hfw.model.entity.Page;
//import com.hfw.model.entity.PageResult;
//import com.hfw.model.jackson.Result;
//import com.hfw.model.po.znew.SysDemo;
//import com.hfw.service.znew.sysDemo.SysDemoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * 系统示例表Controller
// * @author  farkle
// * @date 2025-03-16
// */
//@RestController
//@RequestMapping("/sysDemo")
//public class SysDemoController {
//    @Autowired
//    private SysDemoService sysDemoService;
//
//    @GetMapping("/page")
//    public PageResult<SysDemo> page(Page<SysDemo> page, SysDemo po) {
//        return PageResult.of(sysDemoService.page(page, po));
//    }
//
//    @GetMapping
//    public Result<SysDemo> detail(@RequestParam Long id){
//        return Result.success( sysDemoService.detail( id) );
//    }
//
//    @AdminLog("新增系统示例表")
//    @PostMapping
//    public Result<Void> add(@RequestBody SysDemo sysDemo){
//        return Result.result(sysDemoService.add(sysDemo));
//    }
//
//    @AdminLog("编辑系统示例表")
//    @PutMapping
//    public Result<Void> edit(@RequestBody SysDemo  sysDemo){
//        return Result.result(sysDemoService.edit(sysDemo));
//    }
//
//    @AdminLog("删除系统示例表")
//    @DeleteMapping
//    public Result<Void> del(@RequestParam Long id){
//        return Result.result(sysDemoService.del(id));
//    }
//
//    @AdminLog("批量删除系统示例表")
//    @DeleteMapping("/dels")
//    public Result<Void> dels(@RequestBody List<Long> ids){
//        return Result.result(sysDemoService.dels(ids));
//    }
//
//}
