package com.hfw.admin.controller.sys;

import com.hfw.admin.utils.ResultUtil;
import com.hfw.model.enums.sys.BaseEnum;
import com.hfw.model.jackson.Result;
import com.hfw.model.utils.ChainMap;
import com.hfw.service.component.EnumScan;
import com.hfw.service.sys.sysUpload.SysUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author farkle
 * @date 2022-12-09
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private EnumScan enumScan;
    @Autowired
    private SysUploadService sysUploadService;

    //获取枚举
    private List<ChainMap<String>> getEnums(Class<?> clazz, String filter){
        List<ChainMap<String>> list = new ArrayList<>();
        try {
            Method method = clazz.getMethod("values");
            Object arr = method.invoke(null);
            if(arr.getClass().isArray()){
                Object[] enums = (Object[]) arr;
                for(Object obj : enums){
                    if(filter.contains(obj.toString())){
                        continue;
                    }
                    if(obj instanceof BaseEnum e){
                        ChainMap<String> map = ChainMap.<String>create().putVal("value", e.toString()).putVal("label", e.getDesc());
                        list.add(map);
                    }else{
                        ChainMap<String> map = ChainMap.<String>create().putVal("value", obj.toString()).putVal("label", obj.toString());
                        list.add(map);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            log.error("获取枚举错误",e);
            return list;
        }
    }

    @GetMapping("/enums")
    public Result<List<ChainMap<String>>> enums(@RequestParam String code, @RequestParam(defaultValue = "") String filter, String type){
        Class<?> clazz = null;
        if(type!=null){
            String className  = "com.hfw.model.enums."+type+"."+code;
            clazz = enumScan.getClass(className);
        }else{
            Collection<Class<?>> enumClasses = enumScan.getEnumClasses(code);
            if(!CollectionUtils.isEmpty(enumClasses)){
                clazz = enumClasses.iterator().next();
            }
        }
        return clazz==null ? Result.success(new ArrayList<>()) : Result.success(getEnums(clazz, filter));
    }

    /**
     * 上传文件
     */
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public String upload(@RequestPart MultipartFile file) throws Exception {
        return ResultUtil.jsonData( sysUploadService.upload(file) ,"id","name","url");
    }

}
