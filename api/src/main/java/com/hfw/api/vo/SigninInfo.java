package com.hfw.api.vo;

import lombok.Data;

/**
 * 签到信息
 * @author farkle
 * @date 2023-01-12
 */
@Data
public class SigninInfo {

    /** 用户当前积分 */
    private Integer integral;

    /** 签到状态(1是0否) */
    private Integer signinState = 0;

    /** 连续签到天数 */
    private Integer continueDays = 0;

    /** 今日签到可得积分 */
    private Integer todayIntegral;

    /** 明日签到可得积分 */
    private Integer tomorrowIntegral;

}
