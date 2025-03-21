package com.hfw.admin.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.hfw.model.entity.GeneralException;
import com.hfw.model.enums.sys.ValidCode;
import com.hfw.model.jackson.Result;
import com.hfw.model.utils.ChainMap;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
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
    public Result<Void> generalExceptionHandler(GeneralException e) {
        if (!"prod".equals(profiles)) {
            log.error("GeneralException", e);
        }
        return Result.result(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ModelAndView noResourceFoundException(NoResourceFoundException e) {
        ChainMap model = ChainMap.create()
                .put("status", HttpStatus.NOT_FOUND.value())
                .put("error", HttpStatus.NOT_FOUND.getReasonPhrase())
                .put("timestamp", LocalDateTime.now())
                .put("message", e.getMessage());
        return new ModelAndView("error", model, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> exceptionHandler(Exception e) {
        log.error("全局异常处理",e);
        if ("prod".equals(profiles)) {
            return Result.error("系统错误");
        }
        return Result.error("系统错误:"+e.getMessage());
    }

    //实体类做参数,校验不通过触发
    @ExceptionHandler(BindException.class)
    public Result<Void> bindException(BindException e){
        if (!"prod".equals(profiles)) {
            log.error("GeneralException", e);
        }
        String errorMessage = e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
        return Result.error(errorMessage);
    }
    //@RequestBody修饰的json请求,校验不通过触发
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> methodArgumentNotValidException(MethodArgumentNotValidException e){
        String errorMessage = e.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
        return Result.error(errorMessage);
    }
    //@Email等校验注解直接作用于参数上触发
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> constraintViolationException(ConstraintViolationException e){
        String errorMessage = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
        return Result.error(errorMessage);
    }

    @ExceptionHandler(NotLoginException.class)
    public Result<Void> notLoginException(){
        return Result.result(ValidCode.NO_LOGIN);
    }
    @ExceptionHandler(NotPermissionException.class)
    public Result<Void> notPermissionException(){
        return Result.result(ValidCode.PERMISSION_DENIED);
    }

}
