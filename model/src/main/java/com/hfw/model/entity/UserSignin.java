package com.hfw.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.hfw.common.entity.BaseEntity;
import com.hfw.basesystem.support.validation.ValidGroup;

/**
 * 用户-签到明细
 * @author
 * @date 2023-01-12
 */
@Data
@Accessors(chain = true)
@Table(name = "user_signin")
public class UserSignin extends BaseEntity {

    /** id */
    @NotNull(message = "id不能为空",groups = {ValidGroup.Update.class, ValidGroup.Del.class})
    private Long id;

    /** 用户id */
    @NotNull(message = "用户id不能为空", groups = ValidGroup.Add.class)
    private Long userId;

    /** 签到日期 */
    @NotNull(message = "签到日期不能为空", groups = ValidGroup.Add.class)
    private java.time.LocalDate signinDate;

    /** 签到时间 */
    @NotNull(message = "签到时间不能为空", groups = ValidGroup.Add.class)
    private java.time.LocalDateTime signinTime;

    /** 积分 */
    @NotNull(message = "积分不能为空", groups = ValidGroup.Add.class)
    private Integer integral;

    /** 连续签到天数 */
    @NotNull(message = "连续签到天数不能为空", groups = ValidGroup.Add.class)
    private Integer continueDays;


}