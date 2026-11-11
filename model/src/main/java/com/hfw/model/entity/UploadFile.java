package com.hfw.model.entity;

import com.hfw.model.jackson.ObjectMapperUtil;
import com.hfw.model.po.sys.SysUpload;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UploadFile {
    private Long id;
    private String name;
    private String url;

    public static String removeServerPrefix(String url){
        return SysUpload.removeServerPrefix(url);
    }
    public static void removeServerPrefix(List<UploadFile> list){
        if(list!=null){
            list.forEach(item->item.setUrl(SysUpload.removeServerPrefix(item.getUrl())));
        }
    }
    public static String addServerPrefix(String url){
        return SysUpload.addServerPrefix(url);
    }
    public static void addServerPrefix(List<UploadFile> list){
        if(list!=null){
            list.forEach(item->item.setUrl(SysUpload.addServerPrefix(item.getUrl())));
        }
    }
    public static List<UploadFile> mapToFile(List<Map<String,Object>> list){
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.stream().map(map-> {
            UploadFile file = ObjectMapperUtil.singleton().convertValue(map, UploadFile.class);
            file.setUrl(SysUpload.addServerPrefix(file.getUrl()));
            return file;
        }).toList();
    }
    public static List<Map<String,Object>> fileToMap(List<UploadFile> list){
        if(list==null || list.isEmpty()){
            return null;
        }
        return list.stream().map(file-> {
            Map<String,Object> map = ObjectMapperUtil.singleton().convertValue(file, Map.class);
            map.put("url", SysUpload.removeServerPrefix(file.getUrl()));
            return map;
        }).toList();
    }

}
