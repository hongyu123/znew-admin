package com.hfw.model.po.app;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import com.hfw.model.enums.app.PaymentsType;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户积分明细
 * @author farkle
 * @date 2023-01-12
 */
@Getter @Setter
@Table("user_integral")
public class UserIntegral {
    @TableId
    private Long id;

    /** 用户id */
    private Long userId;

    /** 积分 */
    private Integer integral;

    /** 收支类型 */
    private PaymentsType paymentsType;

    /** 时间 */
    private java.time.LocalDateTime changeTime;

    /** 当前积分 */
    private Integer currentIntegral;

    /** 收入支出来源 */
    private String source;

    /** 来源订单id */
    private Long orderId;

}
