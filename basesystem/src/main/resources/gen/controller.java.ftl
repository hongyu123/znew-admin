package ${packageName}.admin.controller;

import com.hfw.common.entity.PageResult;
import com.hfw.basesystem.support.validation.ValidGroup;
import ${packageName}.common.support.jackson.ApiResult;
import ${packageName}.basesystem.service.CommonService;
import ${packageName}.admin.service.${className}Service;
import ${packageName}.admin.dto.${className}DTO;
import ${packageName}.model.entity.${className};
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CommonService<${className}> commonService;
    @Autowired
    private ${className}Service ${beanName}Service;

    @GetMapping("/page")
    public PageResult page(${className}DTO dto){
        return ${beanName}Service.page(dto);
    }

    @GetMapping("/detail")
    public ApiResult detail(@RequestParam Long id){
        return ApiResult.data( commonService.detail(${className}.class, id) );
    }

    @PostMapping("/add")
    public ApiResult add(@RequestBody @Validated(ValidGroup.Add.class) ${className} ${beanName}){
        commonService.add(${beanName});
        return ApiResult.success();
    }

    @PostMapping("/edit")
    public ApiResult edit(@RequestBody @Validated(ValidGroup.Update.class) ${className} ${beanName}){
        commonService.edit(${beanName});
        return ApiResult.success();
    }

    @PostMapping("/del")
    public ApiResult del(@RequestBody @Validated(ValidGroup.Del.class) ${className} ${beanName}){
        commonService.del(${className}.class, ${beanName}.getId());
        return ApiResult.success();
    }

    @PostMapping("/dels")
    public ApiResult dels(@RequestBody List<Long> ids){
        commonService.dels(${className}.class, ids);
        return ApiResult.success();
    }

}