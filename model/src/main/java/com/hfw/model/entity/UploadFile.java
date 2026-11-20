package com.hfw.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hfw.model.po.sys.SysUpload;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFile {
    //兼容前端element-plus上传组件
    @JsonProperty("uid")
    private Long id;
    private String name;
    /** 不带服务器地址的路径 */
    private String path;
    /** 带服务器地址的全路径url */
    private String url;

    public String getPath(){
        if(path!=null){
            return path;
        }
        if(url!=null){
            return url.replace(SysUpload.SERVER,"");
        }
        return null;
    }
    public String getUrl(){
        if(path!=null){
            return SysUpload.SERVER+path;
        }
        return url;
    }

}
