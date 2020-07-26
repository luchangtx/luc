package com.mrl.common.handle;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.mrl.common.entry.CResponse;
import com.mrl.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理器-基础全局异常处理，各个微服务可以继承此类进行差异化设置
 * 全局处理Controller层抛出的异常
 * @author luc
 * @date 2020/7/2114:00
 */
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CResponse handleException(Exception e){
        log.error("系统内部异常，异常信息："+e);
        return new CResponse().message("系统内部异常："+e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CResponse handleAccessDeniedException(AccessDeniedException e){
        return new CResponse().message("没有权限访问该资源："+e.getMessage());
    }

    /**
     * 业务基础异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CResponse handleBaseException(BaseException e){
        log.error("系统错误",e);
        return new CResponse().message(e.getMessage());
    }

    /**
     * 统一处理请求参数校验（普通传参）
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CResponse handleConstraintViolationException(ConstraintViolationException e){
        StringBuilder message=new StringBuilder();
        Set<ConstraintViolation<?>> violations=e.getConstraintViolations();
        for (ConstraintViolation violation:violations){
            Path path=violation.getPropertyPath();
            String[] pathArr= StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), StringPool.DOT);
            message.append(pathArr[1]).append(violation.getMessage()).append(StringPool.COMMA);
        }
        message=new StringBuilder(message.substring(0,message.length()-1));
        log.error(message.toString());
        return new CResponse().message(message.toString());
    }
    /**
     * 统一处理请求参数校验（实体对象传参）
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CResponse handleBindException(BindException e){
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(StringPool.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        log.error(message.toString());
        return new CResponse().message(message.toString());
    }

    /**
     * 统一处理请求参数校验(json)
     *
     * @param e ConstraintViolationException
     * @return FebsResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(StringPool.COMMA);
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        log.error(message.toString());
        return new CResponse().message(message.toString());
    }
}
