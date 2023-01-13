package com.hfw.basesystem.config;

import com.hfw.common.support.GeneralException;
import com.hfw.common.support.jackson.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 * @author farkle
 * @date 2022-04-08
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active}")
    private String profiles;

    @ExceptionHandler(GeneralException.class)
    public ApiResult generalExceptionHandler(GeneralException e) {
        if (!"prod".equals(profiles)) {
            log.error("GeneralException", e);
        }
        return ApiResult.message(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult exceptionHandler(Exception e) {
        log.error("全局异常处理",e);
        if ("prod".equals(profiles)) {
            return ApiResult.error("系统错误");
        }
        return ApiResult.error("系统错误:"+e.getMessage());
    }


    //实体类做参数,校验不通过触发
    @ExceptionHandler(BindException.class)
    public ApiResult bindException(BindException e){
        if (!"prod".equals(profiles)) {
            log.error("GeneralException", e);
        }
        String errorMessage = e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
        return ApiResult.error(errorMessage);
    }
    //@RequestBody修饰的json请求,校验不通过触发
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult methodArgumentNotValidException(MethodArgumentNotValidException e){
        String errorMessage = e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
        return ApiResult.error(errorMessage);
    }
    //@Email等校验注解直接作用于参数上触发
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult constraintViolationException(ConstraintViolationException e){
        String errorMessage = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        return ApiResult.error(errorMessage);
    }
}
