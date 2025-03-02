package com.hfw.model.po.app;

import cn.xbatis.db.annotations.Table;
import com.hfw.model.entity.BaseEntity;
import com.hfw.model.validation.ValidGroup;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 用户-签到明细
 * @author farkle
 * @date 2023-01-12
 */
@Getter @Setter
@Table("user_signin")
public class UserSignin extends BaseEntity {

    /** id */
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
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