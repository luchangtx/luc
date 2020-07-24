package com.mrl.common.handle;

import com.mrl.common.entry.CResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;

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
        return new CResponse().message("系统内部异常");
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CResponse handleAccessDeniedException(AccessDeniedException e){
        return new CResponse().message("没有权限访问该资源");
    }
}
