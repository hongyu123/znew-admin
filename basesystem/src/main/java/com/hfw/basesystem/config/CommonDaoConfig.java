package com.hfw.basesystem.config;

import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.mybatis.SqlHelper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zyh
 * @date 2022-12-16
 */
@Configuration
public class CommonDaoConfig {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /** 通用dao配置 */
    @Bean
    public CommonDao commonDao(){
        CommonDao commonDao = new CommonDao();
        SqlHelper sqlHelper = new SqlHelper();
        commonDao.setSqlHelper(sqlHelper);
        commonDao.setSqlSessionFactory(sqlSessionFactory);
        return commonDao;
    }

}
