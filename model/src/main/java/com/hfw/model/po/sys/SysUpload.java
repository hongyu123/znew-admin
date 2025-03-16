package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.hfw.model.jackson.Result;
import com.hfw.model.validation.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 系统文件上传
 * @author farkle
 * @date 2023-02-10
 */
@Getter @Setter
@JsonFilter(Result.INCLUDE_FILTER)
@Table("sys_upload")
public class SysUpload {
    public static final String SERVER = "http://localhost:8080/upload";

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
