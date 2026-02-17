package ${packageName}.admin.controller.${projectName};

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import com.hfw.model.mybatis.Where;
import com.hfw.model.mybatis.anno.Sort;
import com.hfw.service.component.CommonService;
import ${packageName}.model.po.${projectName}.${className};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @SaCheckPermission("${beanName}:page")
    @GetMapping("/page")
    public PageResult<${className}> page(Page<${className}> page, ${className} po) {
        Map<String, String> params = page.getParams();
        Where<${className}> where = Where.<${className}>where()
                .eq(po.getId()!=null, ${className}.COLUMN.id, po.getId());
        if(!where.orderBy(page)){
            where.orderBy(${className}.COLUMN.id, Sort.DESC);
        }
        return commonService.page(${className}.class, where, page);
    }

    @SaCheckPermission("${beanName}:view")
    @GetMapping
    public Result<${className}> detail(@RequestParam Long id){
        ${className} po = commonService.selectByPk(${className}.class, id);
        return Result.success(po);
    }

    @AdminLog("新增${tableComment}")
    @SaCheckPermission("${beanName}:add")
    @PostMapping
    public Result<Void> add(@RequestBody ${className} ${beanName}){
        return Result.result(commonService.insert(${beanName}));
    }

    @AdminLog("编辑${tableComment}")
    @SaCheckPermission("${beanName}:edit")
    @PutMapping
    public Result<Void> edit(@RequestBody ${className} ${beanName}){
        return Result.result(commonService.updateByPk(${beanName}));
    }

    @AdminLog("删除${tableComment}")
    @SaCheckPermission("${beanName}:del")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        int res = commonService.deleteByPk(${className}.class, id);
        return Result.result(res);
    }

    @AdminLog("批量删除${tableComment}")
    @SaCheckPermission("${beanName}:del")
    @DeleteMapping("/dels")
    public Result<Void> dels(@RequestBody List<Long> ids){
        int res = commonService.deleteByPks(${className}.class, ids);
        return Result.result(res);
    }

}
