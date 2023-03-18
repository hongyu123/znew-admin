package com.hfw.admin.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.hfw.admin.dto.SysDemoDTO;
import com.hfw.admin.easyexcel.*;
import com.hfw.admin.log.AdminLog;
import com.hfw.admin.service.SysDemoService;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.common.util.RequestUtil;
import com.hfw.model.entity.SysDemo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * 系统示例表控制器
 * @author
 * @date 2023-01-26
 */
@RestController
@RequestMapping("/sysDemo")
public class SysDemoController {
    @Resource
    private SysDemoService sysDemoService;

    @GetMapping("/page")
    public PageResult page(SysDemoDTO dto){
        return sysDemoService.page(dto);
    }

    @GetMapping
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( sysDemoService.detail( id) );
    }

    @AdminLog("新增系统示例表")
    @PostMapping
    public ApiResult add(@RequestBody @Validated(ValidGroup.Add.class) SysDemo sysDemo){
        sysDemoService.add(sysDemo);
        return ApiResult.success();
    }

    @AdminLog("编辑系统示例表")
    @PutMapping
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) SysDemo sysDemo){
        sysDemoService.edit(sysDemo);
        return ApiResult.success();
    }

    @AdminLog("删除系统示例表")
    @DeleteMapping
    public ApiResult del(@RequestParam Long id){
        sysDemoService.del(id);
        return ApiResult.success();
    }

    @AdminLog("批量删除系统示例表")
    @DeleteMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        ids.forEach( id-> sysDemoService.del(id) );
        return ApiResult.success();
    }

    @GetMapping("/export")
    public void export(SysDemoDTO dto, HttpServletResponse response) throws IOException {
        OutputStream os = response.getOutputStream();
        try{
            List<SysDemo> list = sysDemoService.list(dto);
            String fileName = URLEncoder.encode("exp.xlsx", "UTF-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName);
            EasyExcel.write(os,SysDemo.class).autoCloseStream(false).excludeColumnFieldNames(Arrays.asList("id","error"))
                    .sheet().doWrite(list);
        }catch (Exception e){
            e.printStackTrace();
            response.reset();
            RequestUtil.json(response, ApiResult.error(e.getMessage()));
        }finally {
            os.close();
        }
    }

    @Resource
    private ErrorHandler errorHandler;
    @PostMapping("/import")
    public ApiResult<ImportResult> imp(@RequestPart MultipartFile file, @RequestParam(defaultValue = "auto") ImportEnum importType, @RequestParam(defaultValue = "insert") DataHandleEnum dataHandleType) throws IOException {
        try(ExcelReader excelReader = EasyExcel.read(file.getInputStream())/*.extraRead(CellExtraTypeEnum.MERGE)*/.build()){
            ExtListener<SysDemo> listener = new ExtListener<>(sysDemoService, importType, dataHandleType);
            listener.setBacthCnt(5);
            ReadSheet sheet =  EasyExcel.readSheet(0).headRowNumber(1).head(SysDemo.class).registerReadListener(listener).build();
            excelReader.read(sheet);

            ImportResult.ImportResultBuilder builder = ImportResult.builder().importType(importType).successCnt(listener.getSuccessCnt());
            if(listener.hasError()){
                String errorId = errorHandler.save(listener.getErrorList(), listener.getErrorMap());
                builder.failCnt(listener.getFailCnt()).errorId(errorId);
            }
            return ApiResult.data(builder.build());
        }
    }

}