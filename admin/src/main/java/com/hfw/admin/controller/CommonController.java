package com.hfw.admin.controller;

import com.hfw.common.enums.IEnum;
import com.hfw.common.support.ParamMap;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.common.util.DateUtil;
import com.hfw.common.util.StrUtil;
import com.hfw.plugins.objstore.Qiniu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author zyh
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

    @PostMapping("/qiniu")
    public ApiResult qiniu(@RequestPart MultipartFile file) throws IOException {
        return ApiResult.data( Qiniu.upload(file) );
    }

    @Value("${server-url}")
    private String serverUrl;
    @Value("${upload-path}")
    private String uploadPath;
    @PostMapping("/upload")
    public ApiResult upload(@RequestPart MultipartFile file) throws IOException {
        String now = DateUtil.toString(new Date(),"yyyyMM");
        String uploadPath = this.uploadPath+ now;
        Path path = Paths.get(uploadPath);
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        String fileName = UUID.randomUUID().toString() +"."+ StrUtil.getFilenameExtension(file.getOriginalFilename());
        file.transferTo(Paths.get(uploadPath, fileName));
        return ApiResult.data(ParamMap.create().put("url",serverUrl+"/upload/"+now+"/"+fileName));
    }

}
