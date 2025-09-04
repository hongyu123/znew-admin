package ${packageName}.admin.controller.${projectName};

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hfw.admin.log.AdminLog;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.jackson.Result;
import ${packageName}.model.po.${projectName}.${className};
import ${packageName}.service.${projectName}.${beanName}.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private ${className}Service ${beanName}Service;

    @SaCheckPermission("${beanName}:page")
    @GetMapping("/page")
    public PageResult<${className}> page(Page<${className}> page, ${className} po) {
        return PageResult.of(${beanName}Service.page(page, po));
    }

    @SaCheckPermission("${beanName}:view")
    @GetMapping
    public Result<${className}> detail(@RequestParam Long id){
        return Result.success( ${beanName}Service.detail( id) );
    }

    @AdminLog("新增${tableComment}")
    @SaCheckPermission("${beanName}:add")
    @PostMapping
    public Result<Void> add(@RequestBody ${className} ${beanName}){
        return Result.result(${beanName}Service.add(${beanName}));
    }

    @AdminLog("编辑${tableComment}")
    @SaCheckPermission("${beanName}:edit")
    @PutMapping
    public Result<Void> edit(@RequestBody ${className} ${beanName}){
        return Result.result(${beanName}Service.edit(${beanName}));
    }

    @AdminLog("删除${tableComment}")
    @SaCheckPermission("${beanName}:del")
    @DeleteMapping
    public Result<Void> del(@RequestParam Long id){
        return Result.result(${beanName}Service.del(id));
    }

    @AdminLog("批量删除${tableComment}")
    @SaCheckPermission("${beanName}:del")
    @DeleteMapping("/dels")
    public Result<Void> dels(@RequestBody List<Long> ids){
        return Result.result(${beanName}Service.dels(ids));
    }

}
