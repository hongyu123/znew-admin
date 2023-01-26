package com.hfw.api.service.impl;

import com.hfw.api.service.UserIntegralService;
import com.hfw.api.service.UserSigninService;
import com.hfw.api.vo.SigninInfo;
import com.hfw.basesystem.entity.AppUser;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.common.support.GeneralException;
import com.hfw.model.entity.UserSignin;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户签到服务
 * @author farkle
 * @date 2023-01-12
 */
@Service("userSigninService")
public class UserSigninServiceImpl implements UserSigninService {

    @Resource
    private CommonDao commonDao;
    @Resource
    private UserIntegralService userIntegralService;

    //积分计算
    private Integer integral(Integer baseIntegral, Integer plusIntegral, Integer days){
        return baseIntegral+ plusIntegral*(days-1);
    }
    private SigninInfo info(Long userId, Integer baseIntegral, Integer plusIntegral){
        //用户当前积分
        AppUser user = commonDao.selectByPk(AppUser.class, userId);
        SigninInfo info = new SigninInfo();
        info.setIntegral(user.getIntegral());
        info.setTodayIntegral( integral(baseIntegral,plusIntegral,1) );
        info.setTomorrowIntegral( integral(baseIntegral,plusIntegral,2) );

        LocalDate now = LocalDate.now();
        //今日签到情况
        UserSignin today = commonDao.selectOne(new UserSignin().setSigninDate(now).setUserId(userId));
        //昨日签到情况
        UserSignin yesterday = commonDao.selectOne(new UserSignin().setSigninDate(now.plusDays(-1)).setUserId(userId));
        if(yesterday!=null){
            info.setContinueDays(yesterday.getContinueDays());
            info.setTodayIntegral( integral(baseIntegral,plusIntegral,yesterday.getContinueDays()+1) );
            info.setTomorrowIntegral( integral(baseIntegral,plusIntegral,yesterday.getContinueDays()+2) );
        }
        if(today!=null){
            info.setSigninState(1);
            info.setContinueDays(today.getContinueDays());
            info.setTodayIntegral( integral(baseIntegral,plusIntegral,today.getContinueDays()) );
            info.setTomorrowIntegral( integral(baseIntegral,plusIntegral,today.getContinueDays()+1) );
        }
        return info;
    }

    @Override
    public SigninInfo info(Long userId){
        return this.info(userId, 10, 1);
    }

    private void signin(Long userId, Integer baseIntegral, Integer plusIntegral){
        LocalDate now = LocalDate.now();
        //今日签到情况
        UserSignin today = commonDao.selectOne(new UserSignin().setSigninDate(now).setUserId(userId));
        if(today!=null){
            throw new GeneralException("今日已经签到过了哟~");
        }
        //昨日签到情况
        UserSignin yesterday = commonDao.selectOne(new UserSignin().setSigninDate(now.plusDays(-1)).setUserId(userId));
        Integer continueDays = yesterday==null ?1:yesterday.getContinueDays()+1;
        Integer integral = integral(baseIntegral,plusIntegral, continueDays);

        //保存签到记录
        UserSignin userSignin = new UserSignin();
        userSignin.setUserId(userId);
        userSignin.setSigninDate(now);
        userSignin.setSigninTime(LocalDateTime.now());
        userSignin.setIntegral(integral);
        userSignin.setContinueDays(continueDays);
        commonDao.insert(userSignin);
        //积分增加
        userIntegralService.addIntegral(userId, integral, "每日签到", userSignin.getId());
    }

    @Override
    @Transactional
    public void signin(Long userId){
        this.signin(userId, 10, 1);
    }

}
