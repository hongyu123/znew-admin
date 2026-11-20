package ${packageName}.admin.controller.${projectName};

import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import com.hfw.model.mybatis.Where;
import com.hfw.model.mybatis.anno.Sort;
import com.hfw.model.validation.ValidGroup;
import com.hfw.service.component.CommonService;
import ${packageName}.model.po.${projectName}.${className};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * ${tableComment}Controller
 * @author  ${author}
 * @date ${.now?string('yyyy-MM-dd')}
 */
@RestController
@RequestMapping("/${beanName}")
public class ${className}Controller {
    @Autowired
    private CommonService commonService;

    @GetMapping("/page")
    public PageResult<${className}> page(Page<${className}> page, ${className} po) {
        Where<${className}> where = Where.<${className}>where()
                .eq(po.getId()!=null, ${className}.COLUMN.id, po.getId());
        if(!where.orderBy(page)){
            where.orderBy(${className}.COLUMN.id, Sort.DESC);
        }
        return commonService.page(${className}.class, where, page);
    }

    @GetMapping
    public Result<${className}> detail(@RequestParam Long id){
        ${className} po = commonService.selectByPk(${className}.class, id);
        return Result.success(po);
    }

    @AdminLog("新增${tableComment}")
    @PostMapping
    public Result<Void> add(@RequestBody @Validated(ValidGroup.Add.class) ${className} ${beanName}){
        return Result.result(commonService.insert(${beanName}));
    }

    @AdminLog("编辑${tableComment}")
    @PutMapping
    public Result<Void> edit(@RequestBody @Validated(ValidGroup.Update.class) ${className} ${beanName}){
        return Result.result(commonService.updateByPk(${beanName}));
    }

    @AdminLog("删除${tableComment}")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        int res = commonService.deleteByPk(${className}.class, id);
        return Result.result(res);
    }

    @AdminLog("批量删除${tableComment}")
    @DeleteMapping("/dels")
    public Result<Void> dels(@RequestBody List<Long> ids){
        int res = commonService.deleteByPks(${className}.class, ids);
        return Result.result(res);
    }

}
