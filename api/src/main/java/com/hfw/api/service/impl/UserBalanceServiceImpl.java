package com.hfw.api.service.impl;

import com.hfw.api.mapper.AppUserMapper;
import com.hfw.api.service.UserBalanceService;
import com.hfw.basesystem.entity.AppUser;
import com.hfw.basesystem.enums.PaymentsType;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.common.support.GeneralException;
import com.hfw.model.entity.UserBalance;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户余额明细服务
 * @author farkle
 * @date 2023-01-12
 */
@Service("userBalanceService")
public class UserBalanceServiceImpl implements UserBalanceService {

    @Resource
    private AppUserMapper appUserMapper;
    @Resource
    private CommonDao commonDao;

    @Override
    public List<UserBalance> list(UserBalance cond){
        return commonDao.select(cond, cond.getPageNumber(), cond.getPageSize());
    }

    @Override
    public void addBalance(Long userId, Integer balance, String source, Long orderId){
        appUserMapper.addBalance(userId, balance);
        AppUser appUser = commonDao.selectByPk(AppUser.class, userId);
        UserBalance userBalance = new UserBalance();
        userBalance.setUserId(userId);
        userBalance.setBalance(balance);
        userBalance.setPaymentsType(PaymentsType.income);
        userBalance.setChangeTime(LocalDateTime.now());
        if(appUser!=null){
            userBalance.setCurrentBalance(appUser.getBalance());
        }
        userBalance.setSource(source);
        userBalance.setOrderId(orderId);
        commonDao.insert(userBalance);
    }

    @Override
    public void subBalance(Long userId, Integer balance, String source, Long orderId){
        int res = appUserMapper.subBalance(userId, balance);
        if(res<=0){
            throw new GeneralException("用户余额不足!");
        }
        AppUser appUser = commonDao.selectByPk(AppUser.class, userId);
        UserBalance userBalance = new UserBalance();
        userBalance.setUserId(userId);
        userBalance.setBalance(balance);
        userBalance.setPaymentsType(PaymentsType.expenses);
        userBalance.setChangeTime(LocalDateTime.now());
        if(appUser!=null){
            userBalance.setCurrentBalance(appUser.getBalance());
        }
        userBalance.setSource(source);
        userBalance.setOrderId(orderId);
        commonDao.insert(userBalance);
    }

}
