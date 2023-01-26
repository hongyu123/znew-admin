package com.hfw.api.service.impl;

import com.hfw.api.mapper.AppUserMapper;
import com.hfw.api.service.UserIntegralService;
import com.hfw.basesystem.entity.AppUser;
import com.hfw.basesystem.enums.PaymentsType;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.common.support.GeneralException;
import com.hfw.model.entity.UserIntegral;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户积分服务
 * @author farkle
 * @date 2023-01-12
 */
@Service("userIntegralService")
public class UserIntegralServiceImpl implements UserIntegralService {

    @Resource
    private AppUserMapper appUserMapper;
    @Resource
    private CommonDao commonDao;

    @Override
    public List<UserIntegral> list(UserIntegral cond){
        return commonDao.select(cond, cond.getPageNumber(), cond.getPageSize());
    }

    @Override
    public void addIntegral(Long userId, Integer integral, String source, Long orderId){
        appUserMapper.addIntegral(userId, integral);
        AppUser appUser = commonDao.selectByPk(AppUser.class, userId);
        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setUserId(userId);
        userIntegral.setIntegral(integral);
        userIntegral.setPaymentsType(PaymentsType.income);
        userIntegral.setChangeTime(LocalDateTime.now());
        if(appUser!=null){
            userIntegral.setCurrentIntegral(appUser.getIntegral());
        }
        userIntegral.setSource(source);
        userIntegral.setOrderId(orderId);
        commonDao.insert(userIntegral);
    }

    @Override
    public void subIntegral(Long userId, Integer integral, String source, Long orderId){
        int res = appUserMapper.subIntegral(userId, integral);
        if(res<=0){
            throw new GeneralException("用户积分不足!");
        }
        AppUser appUser = commonDao.selectByPk(AppUser.class, userId);
        UserIntegral userIntegral = new UserIntegral();
        userIntegral.setUserId(userId);
        userIntegral.setIntegral(integral);
        userIntegral.setPaymentsType(PaymentsType.expenses);
        userIntegral.setChangeTime(LocalDateTime.now());
        if(appUser!=null){
            userIntegral.setCurrentIntegral(appUser.getIntegral());
        }
        userIntegral.setSource(source);
        userIntegral.setOrderId(orderId);
        commonDao.insert(userIntegral);
    }

}
