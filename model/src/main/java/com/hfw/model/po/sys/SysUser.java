package com.hfw.model.po.sys;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.enums.sys.Gender;
import com.hfw.model.mybatis.Column;
import com.hfw.model.mybatis.anno.Table;
import com.hfw.model.mybatis.anno.TableField;
import com.hfw.model.mybatis.anno.TableId;
import com.hfw.model.utils.ValidUtil;
import com.hfw.model.validation.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 系统用户
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_user")
public class SysUser {

    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    @TableId
    private Long id;

    /** 账号 */
    @NotBlank(message = "账号不能为空", groups = ValidGroup.Add.class)
    @Length(max = 50,message = "账号最多50字符")
    private String account;

    /** 密码 */
    @NotBlank(message = "密码不能为空", groups = ValidGroup.Add.class)
    @Pattern(regexp = ValidUtil.passwordReg, message = "密码格式错误!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /** 组织机构 */
    private Long orgId;

    /** 手机号码 */
    @Pattern(regexp = ValidUtil.phoneReg, message = "手机号码格式错误!")
    private String phone;

    /** 昵称 */
    private String nickname;

    /** 头像 */
    private String avatar;

    /** 邮箱 */
    @Pattern(regexp = ValidUtil.emailReg, message = "邮箱格式错误!")
    private String email;

    /** 性别 */
    private Gender gender;

    /** 状态 */
    @NotNull(message = "状态不能为空", groups = ValidGroup.Add.class)
    private EnableState state;

    /** 是否系统内置 */
    private Integer systemFlag;

    /** 创建账号 */
    @TableField(insertValue = "$CURRENT_USER")
    private String createUser;

    /** 创建时间 */
    @TableField(insertValue = "$NOW")
    private java.time.LocalDateTime createTime;

    /** 更新账号 */
    @TableField(updateValue = "$CURRENT_USER")
    private String updateUser;

    /** 更新时间 */
    @TableField(updateValue = "$NOW")
    private java.time.LocalDateTime updateTime;

    /** 备注 */
    private String remark;


    public enum COLUMN implements Column<SysUser>{
        id,
        account,password,orgId,phone,nickname, avatar,email,gender,state,systemFlag,
        createUser,createTime,updateUser,updateTime,remark
    }

    public SysUser updateFilter(){
        //账号,密码不能修改
        this.setAccount(null);
        this.setPassword(null);
        return this;
    }
}
