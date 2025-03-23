package com.hfw.admin.log;

import com.alibaba.fastjson2.JSON;
import com.hfw.model.po.sys.SysAdminLog;
import com.hfw.model.utils.StrUtil;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysAdminLog.SysAdminLogService;
import com.hfw.service.utils.RequestUtil;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Aspect
public class AdminLogAspect {
    @Autowired
    private SysAdminLogService sysAdminLogService;

    /**
     * 环绕通知
     */
    @Around("@annotation(adminLog)")
    public Object aroundPrintLog(ProceedingJoinPoint point, AdminLog adminLog) throws Throwable {
        SysAdminLog log = new SysAdminLog();
        log.setRequestTime(LocalDateTime.now());
        long start = System.currentTimeMillis();
        //定义返回值
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
            List<Object> argList = new ArrayList();
            for(Object arg : args){
                if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof MultipartFile){
                    continue;
                }
                argList.add(arg);
            }
            log.setRequestBody( StrUtil.limitLength(JSON.toJSONString(argList),2000) );
            //2.执⾏切⼊点⽅法
            returnValue = point.proceed(args);
            //后置通知
            //System.out.println("后置通知");
            log.setResponse( StrUtil.limitLength(JSON.toJSONString(returnValue),2000) );
            log.setTimes(System.currentTimeMillis()-start);
            sysAdminLogService.add(log);
            return returnValue;
        }catch (Throwable t){
            //异常通知
            //System.out.println("异常通知");
            log.setState(0);
            log.setMessage( StrUtil.limitLength(t.getMessage(),255) );
            log.setTimes(System.currentTimeMillis()-start);
            sysAdminLogService.add(log);
            throw t;
        }/*finally {
            //最终通知
            //System.out.println("最终通知");
        }*/
    }

}
