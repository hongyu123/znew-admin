package com.hfw.model.po.sys;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.xbatis.db.annotations.Ignore;
import cn.xbatis.db.annotations.LogicDelete;
import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.hfw.model.enums.sys.Gender;
import com.hfw.model.excel.IEnumConverter;
import com.hfw.model.jackson.Result;
import com.hfw.model.validation.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 系统示例表
 * @author farkle
 * @date 2023-01-05
 */
@Getter @Setter
@JsonFilter(Result.INCLUDE_FILTER)
@Table("sys_demo")
public class SysDemo {

    /** id */
    @ExcelIgnore
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    @TableId
    private Long id;

    /** 名称(文本框) */
    @ExcelProperty("姓名")
    @NotBlank(message = "名称(文本框)不能为空", groups = ValidGroup.Add.class)
    @Length(max = 50,message = "名称(文本框)最多50字符")
    private String name;

    /** 年龄(数字框) */
    @ExcelProperty("年龄")
    private Integer age;

    /** 分数(小数) */
    @ExcelProperty("分数")
    private java.math.BigDecimal score;

    /** 性别(下拉框) */
    @ExcelProperty(value = "性别", converter = IEnumConverter.class)
    private Gender gender;
    public String getGenderDesc(){
        return gender==null ?"":gender.getDesc();
    }

    /** 启用状态(单选框) */
    @ExcelProperty("状态")
    private Integer state;

    /** 兴趣(多选框) */
    @ExcelProperty("兴趣")
    private List<String> interest;

    /** 生日(日期) */
    @ExcelProperty("生日")
    private java.time.LocalDate birth;

    /** 注册时间(日期时间) */
    @ExcelProperty("注册时间")
    @NotNull(message = "注册时间(日期时间)不能为空", groups = ValidGroup.Add.class)
    private java.time.LocalDateTime registTime;

    /** 头像(图片上传) */
    @ExcelProperty("头像")
    @NotBlank(message = "头像(图片上传)不能为空", groups = ValidGroup.Add.class)
    @Length(max = 200,message = "头像(图片上传)最多200字符")
    private String avatar;
    @Ignore
    public String avatarUrl;
    public String getAvatarUrl(){
        return SysUpload.addServerPrefix(this.avatar);
    }

    @ExcelProperty("视频")
    /** 视频(视频上传) */
    private String video;

    /** 照片(多图删除) */
    @ExcelProperty("照片")
    private String photos;

    /** 附件(文件上传) */
    @ExcelProperty("附件")
    private String attachment;

    /** 简介(文本域) */
    @ExcelProperty("简介")
    @NotBlank(message = "简介(文本域)不能为空", groups = ValidGroup.Add.class)
    @Length(max = 255,message = "简介(文本域)最多255字符")
    private String introduction;

    /** 详情(富文本) */
    @ExcelProperty("详情")
    private String detail;

    /** 手机 */
    @ExcelProperty("手机")
    @NotBlank(message = "手机不能为空", groups = ValidGroup.Add.class)
    @Length(max = 11,message = "手机最多11字符")
    private String phone;

    /** 地址 */
    @ExcelProperty("地址")
    private String location;

    /** 经度 */
    @ExcelProperty("经度")
    //@NotNull(message = "经度不能为空", groups = ValidGroup.Add.class)
    private java.math.BigDecimal lng;

    /** 纬度 */
    @ExcelProperty("纬度")
    //@NotNull(message = "纬度不能为空", groups = ValidGroup.Add.class)
    private java.math.BigDecimal lat;

    /** 文件输入 */
    @ExcelIgnore
    private String fileInput;

    /** 逻辑删除 */
    @ExcelIgnore
    @LogicDelete
    private Integer deleted;

    public SysDemo saveFilter(){
        this.setId(null);
        /*this.setUpdator(null);
        this.setUpdateTime(null);*/
        return this;
    }
    public SysDemo updateFilter(){
        /*this.setCreator(null);
        this.setCreateTime(null);*/
        return this;
    }

}
