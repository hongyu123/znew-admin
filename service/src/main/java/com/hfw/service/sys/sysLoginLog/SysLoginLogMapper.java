package com.hfw.service.sys.sysLoginLog;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.enums.sys.LogoutType;
import com.hfw.model.po.sys.SysLoginLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 系统登录日志Mapper
 * @author farkle
 * @date 2025-03-22
 */
public interface SysLoginLogMapper extends MybatisMapper<SysLoginLog> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<SysLoginLog> page(@Param("page") Page<SysLoginLog> page, @Param("po") SysLoginLog po);

    @Update("update sys_login_log set logout_type=#{logoutType}, online_flag=0, logout_time=CURRENT_TIMESTAMP where token=#{token}")
    int logout(@RequestParam("token") String token, @RequestParam("logoutType")LogoutType logoutType);

}
