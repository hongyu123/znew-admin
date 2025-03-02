package com.hfw.model.po.app;

import cn.xbatis.db.annotations.Table;
import com.hfw.model.enums.app.PaymentsType;
import com.hfw.model.entity.BaseEntity;
import com.hfw.model.validation.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
 * 用户余额明细
 * @author farkle
 * @date 2023-01-12
 */
@Getter @Setter
@Table("user_balance")
public class UserBalance extends BaseEntity {

    /** id */
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    private Long id;

    /** 用户id */
    @NotNull(message = "用户id不能为空", groups = ValidGroup.Add.class)
    private Long userId;

    /** 余额 */
    @NotNull(message = "余额不能为空", groups = ValidGroup.Add.class)
    private Integer balance;

    /** 收支类型 */
    @NotNull(message = "收支类型不能为空", groups = ValidGroup.Add.class)
    private PaymentsType paymentsType;

    /** 时间 */
    @NotNull(message = "时间不能为空", groups = ValidGroup.Add.class)
    private java.time.LocalDateTime changeTime;

    /** 当前余额 */
    @NotNull(message = "当前余额不能为空", groups = ValidGroup.Add.class)
    private Integer currentBalance;

    /** 收入支出来源 */
    @NotBlank(message = "收入支出来源不能为空", groups = ValidGroup.Add.class)
    @Length(max = 50,message = "收入支出来源最多50字符")
    private String source;

    /** 来源订单id */
    private Long orderId;

}