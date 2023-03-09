package com.hfw.api.support;

import com.alibaba.fastjson2.JSON;
import com.hfw.basesystem.entity.SysApiLog;
import com.hfw.common.util.RequestUtil;
import com.hfw.common.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 接口日志
 * @author farkle
 * @date 2023-01-13
 */
@Component
@Aspect
public class ApiLogUtil {

    @Resource
    private ApiLogService apiLogService;

    //..任意多个路径,com.hfw.api.controller任意路径下的任意类的任意方法
    @Pointcut("execution(* com.hfw.api.controller..*.*(..))")
    public void pointcut(){}

    /**
     * 环绕通知
     * @return
     */
    @Around("pointcut()")
    public Object aroundPrintLog(ProceedingJoinPoint point) throws Throwable {
        SysApiLog log = new SysApiLog();
        log.setRequestTime(LocalDateTime.now());
        long start = System.currentTimeMillis();
        //定义返回值
        Object returnValue = null;
        try{
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();

            log.setMethod(point.getTarget().getClass().getName()+"."+point.getSignature().getName());
            LoginUser loginUser = LoginUser.getLoginUser();
            if(loginUser!=null){
                log.setUserId(loginUser.getId());
                log.setRequestHeader(loginUser.getToken());
            }
            log.setRequestUrl(request.getRequestURI());
            log.setRequestIp(RequestUtil.getIpAddr(request));

            //前置通知
            // System.out.println("前置通知");
            //1.获取参数
            Object[] args = point.getArgs();
            List argList = new ArrayList();
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
            apiLogService.log(log);
            return returnValue;
        }catch (Throwable t){
            //异常通知
            //System.out.println("异常通知");
            log.setState(0);
            log.setMessage( StrUtil.limitLength(t.getMessage(),255) );
            log.setTimes(System.currentTimeMillis()-start);
            apiLogService.log(log);
            throw t;
        }/*finally {
            //最终通知
            //System.out.println("最终通知");
        }*/
    }

}
