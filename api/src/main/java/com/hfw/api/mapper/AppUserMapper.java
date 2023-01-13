package com.hfw.api.mapper;

import com.hfw.basesystem.entity.AppUser;
import com.hfw.basesystem.entity.AppUserExt;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * App用户Mapper
 * @author farkle
 * @date 2023-01-09
 */
public interface AppUserMapper {

    /**
     * app用户根据扩展信息查询
     * @param ext
     * @return
     */
    AppUser findByExt(AppUserExt ext);

    /**
     * 余额增加
     * @param userId
     * @param price
     * @return
     */
    @Update("update app_user set balance=balance+#{price} where id=#{userId}")
    int addBalance(@Param("userId")Long userId, @Param("price") Integer price);

    /**
     * 余额减少
     * @param userId
     * @param price
     * @return
     */
    @Update("update app_user set balance=balance-#{price} where balance-#{price}>=0 and id=#{userId}")
    int subBalance(@Param("userId")Long userId, @Param("price") Integer price);

    /**
     * 积分增加
     * @param userId
     * @param integral
     * @return
     */
    @Update("update app_user set integral=integral+#{integral} where id=#{userId}")
    int addIntegral(@Param("userId")Long userId, @Param("integral") Integer integral);

    /**
     * 积分减少
     * @param userId
     * @param integral
     * @return
     */
    @Update("update app_user set integral=integral-#{integral} where integral-#{integral}>=0 and id=#{userId}")
    int subIntegral(@Param("userId")Long userId, @Param("integral") Integer integral);

}
