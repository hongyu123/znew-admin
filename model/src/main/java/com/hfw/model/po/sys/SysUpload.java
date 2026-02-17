package com.hfw.model.po.sys;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 系统文件上传
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_upload")
public class SysUpload {

    @TableId
    private Long id;

    /** md5 */
    private String md5;

    /** url */
    private String url;

    /** 文件路径 */
    private String path;

    /** 文件名 */
    private String name;

    /** 文件后缀 */
    private String fileSuffix;

    /** 上传时间 */
    private java.time.LocalDateTime uploadTime;

    /** 文件大小 */
    private Long fileSize;


    public enum COLUMN implements Column<SysUpload>{
        id,
        md5,url,path,name,fileSuffix, uploadTime,fileSize
    }

    public static final String SERVER = "http://localhost:8080/upload";

    public static String removeServerPrefix(String url){
        return url!=null ? url.replace(SERVER,"") : null;
    }
    public static void removeServerPrefix(List<SysUpload> list){
        if(list!=null){
            list.forEach(item->item.setUrl(removeServerPrefix(item.getUrl())));
        }
    }
    public static String addServerPrefix(String url){
        return url!=null ? SERVER+url : null;
    }
    public static void addServerPrefix(List<SysUpload> list){
        if(list!=null){
            list.forEach(item->item.setUrl(addServerPrefix(item.getUrl())));
        }
    }

}

