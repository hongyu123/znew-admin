package com.hfw.basesystem.dto;

import com.hfw.basesystem.entity.SysUser;
import com.hfw.basesystem.support.validation.Pattern;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.util.ValidUtil;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统用户DTO
 */
@Data
public class SysUserDTO extends SysUser {

    //当前登录用户id
    private Long currentUserId;

    @NotNull(message = "角色不能为空", groups = ValidGroup.Add.class)
    @NotEmpty(message = "角色不能为空", groups = ValidGroup.Add.class)
    private List<Long> roleList;

    private String old_password;
    /*************************查询用*****************************/
    private String account_like;
    private String nickname_like;
    private String phone_like;

    /*************************显示用*****************************/

    public SysUser toEntity(){
        SysUser sysUser = new SysUser();
        sysUser.setId(this.getId());
        sysUser.setAccount(this.getAccount());
        sysUser.setPassword(this.getPassword());
        sysUser.setPhone(this.getPhone());
        sysUser.setNickname(this.getNickname());
        sysUser.setAvatar(this.getAvatar());
        sysUser.setEmail(this.getEmail());
        sysUser.setGender(this.getGender());
        sysUser.setState(this.getState());
        sysUser.setSystemFlag(this.getSystemFlag());
        sysUser.setCreator(this.getCreator());
        sysUser.setCreateTime(this.getCreateTime());
        sysUser.setUpdator(this.getUpdator());
        sysUser.setUpdateTime(this.getUpdateTime());
        sysUser.setRemark(this.getRemark());
        return sysUser;
    }
    public SysUserDTO saveFilter(){
        this.setId(null);
        return this;
    }
    public SysUserDTO updateFilter(){
        //账号,密码不能修改
        this.setAccount(null);
        this.setPassword(null);
        return this;
    }

    public static SysUserDTO of(SysUser sysUser){
        SysUserDTO dto = new SysUserDTO();
        dto.setId(sysUser.getId());
        dto.setAccount(sysUser.getAccount());
        dto.setPhone(sysUser.getPhone());
        dto.setNickname(sysUser.getNickname());
        dto.setAvatar(sysUser.getAvatar());
        dto.setEmail(sysUser.getEmail());
        dto.setGender(sysUser.getGender());
        dto.setState(sysUser.getState());
        dto.setRemark(sysUser.getRemark());
        return dto;
    }
}