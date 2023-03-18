package com.hfw.model.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson2.JSON;
import com.hfw.basesystem.easyexcel.IEnumConverter;
import com.hfw.basesystem.entity.SysPicture;
import com.hfw.basesystem.mybatis.FieldIgnore;
import com.hfw.basesystem.mybatis.FieldLogic;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.BaseEntity;
import com.hfw.common.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统示例表
 * @author
 * @date 2023-01-05
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "sys_demo")
public class SysDemo extends BaseEntity {

    /** id */
    @ExcelIgnore
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
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

    /** 启用状态(单选框) */
    @ExcelProperty("状态")
    private Integer state;

    /** 兴趣(多选框) */
    @ExcelProperty("兴趣")
    private String interest;

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
    @NotNull(message = "经度不能为空", groups = ValidGroup.Add.class)
    private java.math.BigDecimal lng;

    /** 纬度 */
    @ExcelProperty("纬度")
    @NotNull(message = "纬度不能为空", groups = ValidGroup.Add.class)
    private java.math.BigDecimal lat;

    /** 文件输入 */
    @ExcelIgnore
    private String fileInput;


    /** 逻辑删除
    @ExcelIgnore
    @FieldLogic
    private Integer delete_flag;*/

    @FieldIgnore
    @ExcelIgnore
    private List<SysPicture> pictureList;

    @FieldIgnore
    @ExcelIgnore
    private List<String> interestList;
    public List<String> getInterestList(){
        if(interestList!=null){
            return interestList;
        }
        if(StringUtils.hasText(interest)){
            return JSON.parseArray(interest,String.class);
        }
        return null;
    }

}