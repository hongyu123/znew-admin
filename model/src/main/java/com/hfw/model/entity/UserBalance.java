package com.hfw.model.entity;

import com.hfw.basesystem.enums.PaymentsType;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import com.hfw.common.entity.BaseEntity;
import com.hfw.basesystem.support.validation.ValidGroup;

/**
 * 用户余额明细
 * @author
 * @date 2023-01-12
 */
@Data
@Accessors(chain = true)
@Table(name = "user_balance")
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