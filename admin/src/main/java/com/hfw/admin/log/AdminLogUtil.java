package com.hfw.admin.log;

import com.alibaba.fastjson2.JSON;
import com.hfw.admin.security.LoginUser;
import com.hfw.basesystem.entity.SysAdminLog;
import com.hfw.basesystem.service.SysAdminLogService;
import com.hfw.common.util.RequestUtil;
import com.hfw.common.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author zyh
 * @date 2022-08-03
 */
@Component
@Aspect
@Slf4j
public class AdminLogUtil {

    @Autowired
    private SysAdminLogService sysAdminLogService;

    /**
     * 环绕通知
     * @return
     */
    @Around("@annotation(adminLog)")
    public Object aroundPrintLog(ProceedingJoinPoint point, AdminLog adminLog){
        //定义返回值
        SysAdminLog log = new SysAdminLog();
        long start = System.currentTimeMillis();
        Object returnValue = null;
        try{
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();

            log.setTitle(adminLog.value());
            log.setMethod(point.getTarget().getClass().getName()+"."+point.getSignature().getName());
            LoginUser loginUser = LoginUser.getLoginUser();
            if(loginUser!=null){
                log.setAccount(loginUser.getUsername());
            }
            log.setRequestUrl(request.getRequestURI());
            log.setRequestIp(RequestUtil.getIpAddr(request));

            //前置通知
           // System.out.println("前置通知");
            //1.获取参数
            Object[] args = point.getArgs();
            log.setRequestBody( StrUtil.limitLength(JSON.toJSONString(args),2000) );
            //2.执⾏切⼊点⽅法
            returnValue = point.proceed(args);
            //后置通知
            //System.out.println("后置通知");

            log.setResponse( StrUtil.limitLength(JSON.toJSONString(returnValue),2000) );
        }catch (Throwable t){
            //异常通知
            //System.out.println("异常通知");
            log.setState(0);
            log.setMessage( StrUtil.limitLength(t.getMessage(),255) );
            this.log.error("异常通知",t);
        }finally {
            //最终通知
            //System.out.println("最终通知");
        }
        log.setTimes(System.currentTimeMillis()-start);
        log.setRequestTime(LocalDateTime.now());
        try{
            sysAdminLogService.log(log);
        }catch (Exception e){
            this.log.error("AOP日志入库异常", e);
        }
        return returnValue;
    }

}
