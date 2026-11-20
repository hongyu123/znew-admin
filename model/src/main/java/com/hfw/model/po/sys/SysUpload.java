package com.hfw.model.po.sys;

import com.hfw.model.mybatis.Column;
import com.hfw.model.mybatis.anno.Table;
import com.hfw.model.mybatis.anno.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统文件上传
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_upload")
public class SysUpload {
    public static final String SERVER = "http://localhost:8080/file";

    @TableId
    private Long id;

    /** md5 */
    private String md5;

    /** 文件路径 */
    private String filePath;

    /** 不带服务器地址的url */
    private String path;

    /** 带服务器地址的全路径url */
    private String url;

    /** 文件名 */
    private String name;

    /** 文件后缀 */
    private String fileSuffix;

    /** 上传用户 */
    private String uploadUser;

    /** 上传时间 */
    private java.time.LocalDateTime uploadTime;

    /** 文件大小 */
    private Long fileSize;


    public enum COLUMN implements Column<SysUpload>{
        id,
        md5,url,path,name,fileSuffix, uploadTime,fileSize
    }

}

