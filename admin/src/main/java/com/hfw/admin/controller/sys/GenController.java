package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.fastjson2.JSONObject;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysGenTable;
import com.hfw.service.sys.gen.GenService;
import com.hfw.service.sys.gen.SysGenTableService;
import com.hfw.service.sys.gen.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.StringJoiner;

/**
 * 代码生成器Controller
 * @author farkle
 * @date 2022-04-15
 */
@RestController
@RequestMapping("/gen")
public class GenController {
    @Autowired
    private GenService genService;

    //表分页列表
    @SaCheckPermission("gen:page")
    @GetMapping("/tablePage")
    public PageResult<Table> tableList(Page<Table> page, String tableName){
        return PageResult.of(genService.tablePage(page, tableName));
    }

    //代码生成到配置路径
    @SaCheckPermission("gen:gen")
    @PostMapping("/genToPath")
    public Result<Void> genToPath(@RequestBody JSONObject params) throws Exception {
        return genService.genToPath(params.getString("tableName"));
    }
    //代码批量生成到配置路径
    @SaCheckPermission("gen:gen")
    @PostMapping("/batchGenToPath")
    public Result<Void> batchGenToPath(@RequestBody List<String> tables) throws Exception{
        StringJoiner errors = new StringJoiner("<br/>");
        if(!CollectionUtils.isEmpty(tables)){
            for(String table : tables){
                Result<Void> result = genService.genToPath(table);
                if(!result.successful()){
                    errors.add(result.getMessage());
                }
            }
        }
        return errors.length()>0 ? Result.error(errors.toString()) : Result.success();
    }

    //代码生成到项目路径
    @SaCheckPermission("gen:genToProject")
    @PostMapping("/genToProject")
    public Result<Void> genToProject(@RequestBody JSONObject params) throws Exception {
        return genService.genToProject(params.getString("tableName"));
    }
    //代码批量生成到项目路径
    @SaCheckPermission("gen:genToProject")
    @PostMapping("/batchGenToProject")
    public Result<Void> batchGenToProject(@RequestBody List<String> tables) throws Exception{
        StringJoiner errors = new StringJoiner("<br/>");
        if(!CollectionUtils.isEmpty(tables)){
            for(String table : tables){
                Result<Void> result = genService.genToProject(table);
                if(!result.successful()){
                    errors.add(result.getMessage());
                }
            }
        }
        return errors.length()>0 ? Result.error(errors.toString()) : Result.success();
    }

    //根据表名获取表单配置信息
    @SaCheckPermission("gen:gen")
    @GetMapping("/formInfo")
    public Result<SysGenTable> formGenTableInfo(@RequestParam String tableName){
        return Result.success( genService.formGenTableInfo(tableName) );
    }
    //表单生成到配置路径
    @SaCheckPermission("gen:gen")
    @PostMapping("/genFormToPath")
    public Result<Void> genFormToPath(@RequestBody SysGenTable table) throws Exception {
        genService.genFormToPath(table);
        return Result.success();
    }
    //表单生成到项目路径
    @SaCheckPermission("gen:genToProject")
    @PostMapping("/genFormToProject")
    public Result<Void> genFormToProject(@RequestBody SysGenTable table) throws Exception {
        genService.genFormToProject(table);
        return Result.success();
    }

    //java代码生成预览
    @SaCheckPermission("gen:genView")
    @GetMapping("/javaCode")
    public Result<String> javaCode(@RequestParam String tableName, @RequestParam String templateName) throws Exception {
        return genService.javaCode(tableName,templateName);
    }

    //表单生成代码预览
    @SaCheckPermission("gen:genView")
    @PostMapping("/formCode/{templateName}")
    public Result<String> formCode(@RequestBody SysGenTable table, @PathVariable("templateName") String templateName) throws Exception {
        return Result.string( genService.formCode(table,templateName) );
    }
    @Autowired
    private SysGenTableService sysGenTableService;
    //表单生成代码预览
    @SaCheckPermission("gen:genView")
    @GetMapping("/formCode")
    public Result<String> formCode(@RequestParam Long id, @RequestParam String templateName) throws Exception {
        SysGenTable table = sysGenTableService.detail(id);
        return Result.string( genService.formCode(table,templateName) );
    }

}
