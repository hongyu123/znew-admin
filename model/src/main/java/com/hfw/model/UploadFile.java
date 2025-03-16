package com.hfw.model;

import com.hfw.model.po.sys.SysUpload;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

}
