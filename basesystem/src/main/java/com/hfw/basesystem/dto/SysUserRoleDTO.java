package com.hfw.basesystem.dto;

import com.hfw.common.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zyh
 * @date 2022-12-28
 */
@Data
public class SysUserRoleDTO extends BaseEntity {
    @NotNull(message = "角色id不能为空")
    private Long roleId;
    @NotEmpty(message = "角色授权用户不能为空")
    private List<Long> userIds;

    /*************************查询用*****************************/
    private String account_like;
    private String phone_like;
    private String nickname_like;

}
