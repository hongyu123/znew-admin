package com.hfw.api.controller;

import com.hfw.api.service.UserBalanceService;
import com.hfw.api.service.UserIntegralService;
import com.hfw.api.service.UserSigninService;
import com.hfw.api.support.LoginUser;
import com.hfw.api.vo.SigninInfo;
import com.hfw.common.enums.SortByWay;
import com.hfw.common.support.jackson.ApiResult;
import com.hfw.model.entity.UserBalance;
import com.hfw.model.entity.UserIntegral;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户积分
 * @author farkle
 * @date 2023-01-12
 */
@RestController
@RequestMapping("/user/integral")
public class UserIntegralController {

    @Resource
    private UserIntegralService userIntegralService;
    @Resource
    private UserSigninService userSigninService;

    /**
     * 积分明细列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public ApiResult<List<UserIntegral>> list(Integer pageNumber, Integer pageSize){
        LoginUser loginUser = LoginUser.getLoginUser();
        UserIntegral cond = (UserIntegral) new UserIntegral().setUserId(loginUser.getId()).setPageNumber(pageNumber).setPageSize(pageSize)
                .setSortByField("id").setSortByWay(SortByWay.desc);
        List<UserIntegral> list = userIntegralService.list(cond);
        return ApiResult.list(list);
    }

    /**
     * 签到信息
     * @return
     */
    @GetMapping("/signinInfo")
    public ApiResult<SigninInfo> signinInfo(){
        SigninInfo info = userSigninService.info(LoginUser.getLoginUser().getId());
        return ApiResult.data(info);
    }

    /**
     * 签到
     * @return
     */
    @PostMapping("/signin")
    public ApiResult signin(){
        userSigninService.signin(LoginUser.getLoginUser().getId());
        return ApiResult.success();
    }

    /**
     * 积分支出
     * @param integral
     * @param source
     * @param orderId
     * @return
     */
    @PostMapping("/sub")
    public ApiResult subIntegral(Integer integral, String source, Long orderId){
        LoginUser loginUser = LoginUser.getLoginUser();
        userIntegralService.subIntegral(loginUser.getId(), integral, source, orderId);
        return ApiResult.success();
    }


    @Resource
    private UserBalanceService userBalanceService;
    /**
     * 余额明细列表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping("/balance/list")
    public ApiResult<List<UserBalance>> balanceList(Integer pageNumber, Integer pageSize){
        LoginUser loginUser = LoginUser.getLoginUser();
        UserBalance cond = (UserBalance) new UserBalance().setUserId(loginUser.getId()).setPageNumber(pageNumber).setPageSize(pageSize)
                .setSortByField("id").setSortByWay(SortByWay.desc);
        List<UserBalance> list = userBalanceService.list(cond);
        return ApiResult.list(list);
    }

    /**
     * 余额收入
     * @param balance 金额
     * @param source 来源
     * @param orderId 订单id
     * @return
     */
    @PostMapping("/balance/add")
    public ApiResult addBalance(Integer balance, String source, Long orderId){
        LoginUser loginUser = LoginUser.getLoginUser();
        userBalanceService.addBalance(loginUser.getId(), balance,source,orderId);
        return ApiResult.success();
    }

    /**
     * 余额支出
     * @param balance
     * @param source
     * @param orderId
     * @return
     */
    @PostMapping("/balance/sub")
    public ApiResult subBalance(Integer balance, String source, Long orderId){
        LoginUser loginUser = LoginUser.getLoginUser();
        userBalanceService.subBalance(loginUser.getId(), balance,source,orderId);
        return ApiResult.success();
    }
}
