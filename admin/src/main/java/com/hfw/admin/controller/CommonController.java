package com.hfw.admin.controller;

import com.hfw.basesystem.service.SysUploadService;
import com.hfw.common.enums.IEnum;
import com.hfw.common.support.ParamMap;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author farkle
 * @date 2022-12-09
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    //获取枚举
    private List<ParamMap> getEnums(String className, String filter){
        List<ParamMap> list = new ArrayList<>();
        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getMethod("values");
            Object arr = method.invoke(null);
            if(arr.getClass().isArray()){
                Object[] enums = (Object[]) arr;
                for(Object e : enums){
                    if(filter.contains(e.toString())){
                        continue;
                    }
                    if(e instanceof IEnum){
                        IEnum ie = (IEnum) e;
                        ParamMap map = ParamMap.create().put("value", ie.toString()).put("label", ie.getDesc());
                        list.add(map);
                    }else{
                        ParamMap map = ParamMap.create().put("value", e.toString()).put("label", e.toString());
                        list.add(map);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    @GetMapping("/enum/common")
    public ApiResult commonEnum(
            @RequestParam String code,
            @RequestParam(defaultValue = "") String filter){
        String className  = "com.hfw.common.enums."+code;
        return ApiResult.list( this.getEnums(className,filter) );
    }
    @GetMapping("/enum/base")
    public ApiResult baseEnum(
            @RequestParam String code,
            @RequestParam(defaultValue = "") String filter){
        String className  = "com.hfw.basesystem.enums."+code;
        return ApiResult.list( this.getEnums(className,filter) );
    }
    @GetMapping("/enum/model")
    public ApiResult modelEnum(
            @RequestParam String code,
            @RequestParam(defaultValue = "") String filter){
        String className  = "com.hfw.model.enums."+code;
        return ApiResult.list( this.getEnums(className,filter) );
    }

    @Resource
    private SysUploadService sysUploadService;

    @PostMapping("/upload")
    public ApiResult upload(@RequestPart MultipartFile file) throws Exception {
        return ApiResult.data( sysUploadService.upload(file) );
    }

}
