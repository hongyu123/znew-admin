package com.hfw.service.sys.sysLoginLog;

import com.hfw.model.enums.sys.LogoutType;
import com.hfw.model.mybatis.BaseMapper;
import com.hfw.model.po.sys.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 系统登录日志Mapper
 * @author farkle
 * @date 2025-03-22
 */
@Mapper
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    @Update("update sys_login_log set logout_type=#{logoutType}, online_flag=0, logout_time=CURRENT_TIMESTAMP where id=#{id}")
    int logout(@RequestParam("id") Long id, @RequestParam("logoutType")LogoutType logoutType);

    // 用户登录过期
    @Update("update sys_login_log set logout_type=1, online_flag=0, logout_time=CURRENT_TIMESTAMP where online_flag=1 and user_id=#{userId} and expire_time<CURRENT_TIMESTAMP")
    int expire(Long userId);

}
