package ${packageName}.admin.controller;

import com.hfw.admin.log.AdminLog;
import com.hfw.common.entity.PageResult;
import com.hfw.basesystem.support.validation.ValidGroup;
import ${packageName}.common.support.jackson.ApiResult;
import ${packageName}.basesystem.service.CommonService;
import ${packageName}.admin.service.${className}Service;
import ${packageName}.admin.dto.${className}DTO;
import ${packageName}.model.entity.${className};
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ${tableComment}控制器
 * @author  ${author}
 * @date ${.now?string('yyyy-MM-dd')}
 */
@RestController
@RequestMapping("/${beanName}")
public class ${className}Controller {

    @Resource
    private CommonService<${className}> commonService;
    @Resource
    private ${className}Service ${beanName}Service;

    @GetMapping("/page")
    public PageResult page(${className}DTO dto){
        return ${beanName}Service.page(dto);
    }

    @GetMapping
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( commonService.getById(${className}.class, id) );
    }

    @AdminLog("新增${tableComment}")
    @PostMapping
    public ApiResult add(@RequestBody @Validated(ValidGroup.Add.class) ${className} ${beanName}){
        commonService.save(${beanName});
        return ApiResult.success();
    }

    @AdminLog("编辑${tableComment}")
    @PutMapping
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) ${className} ${beanName}){
        commonService.edit(${beanName});
        return ApiResult.success();
    }

    @AdminLog("删除${tableComment}")
    @DeleteMapping
    public ApiResult del(@RequestParam Long id){
        commonService.del(${className}.class, id);
        return ApiResult.success();
    }

    @AdminLog("批量删除${tableComment}")
    @DeleteMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        commonService.dels(${className}.class, ids);
        return ApiResult.success();
    }

}