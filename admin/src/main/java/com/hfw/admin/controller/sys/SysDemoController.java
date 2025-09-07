package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.idev.excel.EasyExcel;
import cn.idev.excel.ExcelReader;
import cn.idev.excel.ExcelWriter;
import cn.idev.excel.enums.CellExtraTypeEnum;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.write.metadata.WriteSheet;
import cn.idev.excel.write.metadata.fill.FillConfig;
import cn.idev.excel.write.metadata.fill.FillWrapper;
import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.enums.sys.SortByWay;
import com.hfw.model.excel.PageReadExceptListener;
import com.hfw.model.jackson.Result;
import com.hfw.model.po.sys.SysDemo;
import com.hfw.model.validation.ValidGroup;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysDemo.SysDemoDTO;
import com.hfw.service.sys.sysDemo.SysDemoService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 系统示例表控制器
 * @author farkle
 * @date 2023-01-26
 */
@RestController
@RequestMapping("/sysDemo")
public class SysDemoController {
    @Autowired
    private SysDemoService sysDemoService;

    @GetMapping("/page")
    @SaCheckPermission("sysDemo:page")
    public PageResult<SysDemo> page(Page<SysDemo> page, SysDemo po) {
        LoginUser.enableDataScope("sys_demo");//开启数据权限
        page.sortDefault(SortByWay.desc,"id");
        return PageResult.of(sysDemoService.page(page, po));
    }

    @GetMapping
    @SaCheckPermission("sysDemo:view")
    public Result<SysDemoDTO> detail(@RequestParam Long id){
        return Result.success( sysDemoService.detail( id) );
    }

    @AdminLog("新增系统示例表")
    @SaCheckPermission("sysDemo:add")
    @PostMapping
    public Result<Void> add(@RequestBody @Validated(ValidGroup.Add.class) SysDemoDTO sysDemo){
        return Result.result(sysDemoService.add(sysDemo));
    }

    @AdminLog("编辑系统示例表")
    @SaCheckPermission("sysDemo:edit")
    @PutMapping
    public Result<Void> edit(@RequestBody @Validated(ValidGroup.Update.class) SysDemoDTO sysDemo){
        return Result.result(sysDemoService.edit(sysDemo));
    }

    @AdminLog("删除系统示例表")
    @SaCheckPermission("sysDemo:del")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        return Result.result(sysDemoService.del(id));
    }

    @AdminLog("批量删除系统示例表")
    @SaCheckPermission("sysDemo:del")
    @DeleteMapping("/dels")
    public Result<Void> dels(@RequestBody List<Long> ids){
        return Result.result(sysDemoService.dels(ids));
    }

    //实体类注解导出
    @GetMapping("/export")
    public void export(SysDemo po, HttpServletResponse response) throws IOException {
        Page<SysDemo> page = Page.nonePage();
        page.sortDefault(SortByWay.desc,"id");
        page = sysDemoService.page(page, po);

        String fileName = URLEncoder.encode("系统示例.xlsx", StandardCharsets.UTF_8);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);
        OutputStream os = response.getOutputStream();
        try(os){
            EasyExcel.write(os,SysDemo.class).autoCloseStream(false).excludeColumnFieldNames(List.of("id"))
                    .sheet().doWrite(page.getList());
        }
    }
    //模板文件导出
    @GetMapping("/exp_tmp")
    public void exportTemplate(SysDemo po, HttpServletResponse response) throws IOException {
        Page<SysDemo> page = Page.nonePage();
        page.sortDefault(SortByWay.desc,"id");
        page = sysDemoService.page(page, po);

        String fileName = URLEncoder.encode("exp_tmp.xlsx", StandardCharsets.UTF_8);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);

        OutputStream os = response.getOutputStream();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("template/demo.xlsx");
        ExcelWriter excelWriter = EasyExcel.write(os).withTemplate(is).build();
        try(os; is; excelWriter){
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            FillConfig fillConfig = FillConfig.builder().build();
            excelWriter.fill(new FillWrapper("list", page.getList()), fillConfig, writeSheet);
            //excelWriter.fill(page.getList(), writeSheet);
        }
    }
    //导入
    @PostMapping("/import")
    public Result<Void> imp(@RequestPart MultipartFile file) throws IOException {
        InputStream is = file.getInputStream();
        ExcelReader excelReader = EasyExcel.read(is).extraRead(CellExtraTypeEnum.MERGE).build();
        try(is; excelReader){
            PageReadExceptListener<SysDemo> listener = new PageReadExceptListener<>(list->sysDemoService.saveBatch(list));
            ReadSheet sheet =  EasyExcel.readSheet(0).headRowNumber(1).head(SysDemo.class).registerReadListener(listener).build();
            excelReader.read(sheet);
            List<String> errorList = listener.getErrorList();
            if(!CollectionUtils.isEmpty(errorList)){
                return Result.error(StringUtils.join(errorList, "\r\n"));
            }
        }
        return Result.success();
    }

}