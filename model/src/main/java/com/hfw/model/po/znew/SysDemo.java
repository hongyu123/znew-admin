package com.hfw.model.po.znew;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import com.hfw.model.enums.sys.Gender;
import com.hfw.model.validation.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统示例
 * @author farkle
 * @date 2026-11-20
 */
@Getter
@Setter
@Table("sys_demo")
public class SysDemo {

    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    @TableId
    private Long id;

    @NotBlank(message = "名称(文本框)不能为空", groups = ValidGroup.Add.class)
    @Length(max = 50,message = "名称(文本框)最多50字符")
    /** 名称(文本框) */
    private String name;

    /** 年龄(数字框) */
    private Integer age;

    /** 分数(小数) */
    private java.math.BigDecimal score;

    /** 性别(下拉框) */
    private Gender gender;
    public String getGenderDesc(){
        return gender==null ?"":gender.getDesc();
    }

    /** 启用状态(单选框) */
    private Integer state;

    /** 兴趣(多选框) */
    private String interest;

    /** 生日(日期) */
    private java.time.LocalDate birth;

    @NotNull(message = "注册时间(日期时间)不能为空", groups = ValidGroup.Add.class)
    /** 注册时间(日期时间) */
    private java.time.LocalDateTime registTime;

    @NotBlank(message = "头像(图片上传)不能为空", groups = ValidGroup.Add.class)
    @Length(max = 200,message = "头像(图片上传)最多200字符")
    /** 头像(图片上传) */
    private String avatar;

    /** 视频(视频上传) */
    private String video;

    /** 照片(多图上传) */
    private String photos;

    /** 附件(文件上传) */
    private String attachment;

    @NotBlank(message = "简介(文本域)不能为空", groups = ValidGroup.Add.class)
    @Length(max = 255,message = "简介(文本域)最多255字符")
    /** 简介(文本域) */
    private String introduction;

    /** 详情(富文本) */
    private String detail;

    @NotBlank(message = "手机不能为空", groups = ValidGroup.Add.class)
    @Length(max = 11,message = "手机最多11字符")
    /** 手机 */
    private String phone;

    /** 地址 */
    private String location;

    /** 经度 */
    private java.math.BigDecimal lng;

    /** 纬度 */
    private java.math.BigDecimal lat;

    /** 文件输入 */
    private String fileInput;

    /** user_id */
    private Long userId;

    /** org_id */
    private Long orgId;

    /** 逻辑删除 */
    @LogicDelete
    private Integer deleted;

    public enum COLUMN implements Column<SysDemo>{
        id,
        name,age,score,gender,state, interest,birth,registTime,avatar,video,
        photos,attachment,introduction,detail,phone, location,lng,lat,fileInput,userId,
        orgId,
        deleted
    }
}
