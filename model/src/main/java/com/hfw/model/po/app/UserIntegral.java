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
 * 用户积分明细
 * @author farkle
 * @date 2023-01-12
 */
@Getter @Setter
@Table("user_integral")
public class UserIntegral extends BaseEntity {

    /** id */
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    private Long id;

    /** 用户id */
    @NotNull(message = "用户id不能为空", groups = ValidGroup.Add.class)
    private Long userId;

    /** 积分 */
    @NotNull(message = "积分不能为空", groups = ValidGroup.Add.class)
    private Integer integral;

    /** 收支类型 */
    @NotNull(message = "收支类型不能为空", groups = ValidGroup.Add.class)
    private PaymentsType paymentsType;

    /** 时间 */
    @NotNull(message = "时间不能为空", groups = ValidGroup.Add.class)
    private java.time.LocalDateTime changeTime;

    /** 当前积分 */
    @NotNull(message = "当前积分不能为空", groups = ValidGroup.Add.class)
    private Integer currentIntegral;

    /** 收入支出来源 */
    @NotBlank(message = "收入支出来源不能为空", groups = ValidGroup.Add.class)
    @Length(max = 50,message = "收入支出来源最多50字符")
    private String source;

    /** 来源订单id */
    private Long orderId;

}