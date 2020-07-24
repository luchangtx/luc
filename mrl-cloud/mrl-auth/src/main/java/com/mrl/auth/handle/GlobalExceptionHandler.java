package com.mrl.auth.handle;

import com.mrl.common.entry.CResponse;
import com.mrl.common.exception.ValidateCodeException;
import com.mrl.common.handle.BaseExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 权限认证服务 全局异常处理类
 * @author luc
 * @date 2020/7/2114:21
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler extends BaseExceptionHandler {

    /**
     * 此处有坑
     * 全局异常只能拦截 controller 控制器层抛出的异常，Filter中的异常无法捕获
     * 要在Spring security 过滤器链中重用 全局异常捕获功能，需要 在 Filter 中 使用 HandlerExceptionResolver 将异常重定向到 全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ValidateCodeException.class)
    public CResponse handleBaseException(ValidateCodeException e){
        log.error("ValidateCodeException："+e.getMessage());
        return new CResponse().message(e.getMessage());
    }
}
