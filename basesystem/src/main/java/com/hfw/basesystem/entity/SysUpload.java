package com.hfw.basesystem.entity;

import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统文件上传
 * @author farkle
 * @date 2023-02-10
 */
@Data
@Accessors(chain = true)
@Table(name = "sys_upload")
public class SysUpload extends BaseEntity {

    /** id */
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    private Long id;

    /** md5 */
    @NotBlank(message = "md5不能为空", groups = ValidGroup.Add.class)
    @Length(max = 60,message = "md5最多60字符")
    private String md5;

    /** url */
    @NotBlank(message = "url不能为空", groups = ValidGroup.Add.class)
    @Length(max = 200,message = "url最多200字符")
    private String url;

    /** 文件路径(id) */
    @NotBlank(message = "文件路径(id)不能为空", groups = ValidGroup.Add.class)
    @Length(max = 100,message = "文件路径(id)最多100字符")
    private String path;

    /** 文件名 */
    private String fileName;

    /** 文件后缀 */
    private String fileSuffix;

    /** 上传时间 */
    private java.time.LocalDateTime uploadTime;

    /** 文件大小 */
    private Long fileSize;

}