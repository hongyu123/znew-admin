package com.hfw.model.po.app;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户-签到明细
 * @author farkle
 * @date 2023-01-12
 */
@Getter @Setter
@Table("user_signin")
public class UserSignin {
    @TableId
    private Long id;

    /** 用户id */
    private Long userId;

    /** 签到日期 */
    private java.time.LocalDate signinDate;

    /** 签到时间 */
    private java.time.LocalDateTime signinTime;

    /** 积分 */
    private Integer integral;

    /** 连续签到天数 */
    private Integer continueDays;

}
