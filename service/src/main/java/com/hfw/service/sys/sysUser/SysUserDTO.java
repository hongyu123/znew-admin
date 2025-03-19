package com.hfw.service.sys.sysUser;

import com.hfw.model.po.sys.SysUser;
import com.hfw.model.utils.BeanCopierUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SysUserDTO extends SysUser {
    private List<Long> roleList;
    private String oldPassword;

    public SysUser toPo(){
        SysUser po = new SysUser();
        BeanCopierUtil.copyProperties(this, po);
        return po;
    }
    public static SysUserDTO of(SysUser sysUser){
        SysUserDTO dto = new SysUserDTO();
        BeanCopierUtil.copyProperties(sysUser, dto);
        return dto;
    }

}
