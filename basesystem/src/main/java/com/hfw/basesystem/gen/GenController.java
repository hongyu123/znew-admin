package com.hfw.basesystem.gen;

import com.alibaba.fastjson2.JSONObject;
import com.hfw.basesystem.entity.SysGenTable;
import com.hfw.basesystem.service.SysGenTableService;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/tablePage")
    public PageResult<Table> tableList(String tableName, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return genService.tablePage(tableName, pageNumber,pageSize);
    }

    //代码生成到配置路径
    @PostMapping("/genToPath")
    public ApiResult genToPath(@RequestBody JSONObject params) throws Exception {
        genService.genToPath(params.getString("tableName"));
        return ApiResult.success();
    }
    //代码批量生成到配置路径
    @PostMapping("/batchGenToPath")
    public ApiResult batchGenToPath(@RequestBody List<String> tables) throws Exception{
        if(!CollectionUtils.isEmpty(tables)){
            for(String table : tables){
                genService.genToPath(table);
            }
        }
        return ApiResult.success();
    }

    //代码生成到项目路径
    @PostMapping("/genToProject")
    public ApiResult genToProject(@RequestBody JSONObject params) throws Exception {
        genService.genToProject(params.getString("tableName"));
        return ApiResult.success();
    }
    //代码批量生成到项目路径
    @PostMapping("/batchGenToProject")
    public ApiResult batchGenToProject(@RequestBody List<String> tables) throws Exception{
        if(!CollectionUtils.isEmpty(tables)){
            for(String table : tables){
                genService.genToProject(table);
            }
        }
        return ApiResult.success();
    }

    //根据表名获取表单配置信息
    @GetMapping("/formInfo")
    public ApiResult formGenTableInfo(@RequestParam String tableName){
        return ApiResult.data( genService.formGenTableInfo(tableName) );
    }
    //表单生成到配置路径
    @PostMapping("/genFormToPath")
    public ApiResult genFormToPath(@RequestBody SysGenTable table) throws Exception {
        genService.genFormToPath(table);
        return ApiResult.success();
    }
    //表单生成到项目路径
    @PostMapping("/genFormToProject")
    public ApiResult genFormToProject(@RequestBody SysGenTable table) throws Exception {
        genService.genFormToProject(table);
        return ApiResult.success();
    }

    //java代码生成预览
    @GetMapping("/javaCode")
    public ApiResult javaCode(@RequestParam String tableName, @RequestParam String templateName) throws Exception {
        return ApiResult.data( genService.javaCode(tableName,templateName) );
    }

    //表单生成代码预览
    @PostMapping("/formCode/{templateName}")
    public ApiResult formCode(@RequestBody SysGenTable table, @PathVariable("templateName") String templateName) throws Exception {
        return ApiResult.data( genService.formCode(table,templateName) );
    }
    @Autowired
    private SysGenTableService sysGenTableService;
    //表单生成代码预览
    @GetMapping("/formCode")
    public ApiResult formCode(@RequestParam Long id, @RequestParam String templateName) throws Exception {
        SysGenTable table = sysGenTableService.detail(id);
        return ApiResult.data( genService.formCode(table,templateName) );
    }

}
