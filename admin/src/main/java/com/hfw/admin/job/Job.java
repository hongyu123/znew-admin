package com.hfw.admin.job;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务
 * @author farkle
 * @date 2023-01-31
 */
//@Component
//@EnableScheduling
@Slf4j
public class Job {

//    @Scheduled(cron = "0/1 * * * * ?") //每秒执行一次
//    @Scheduled(cron = "0 0/1 * * * ?") //每分钟执行一次
//    @Scheduled(cron = "0 0 0/1 * * ?") //每小时执行一次
//    @Scheduled(cron = "0 0 0 * * ?") //每天执行一次
//    @Scheduled(cron = "0 0 9 * * ?") //每天定时9点执行

    public void test(){
        System.out.println("-----------");
    }

}
