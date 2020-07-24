package com.mrl.common.config;

import com.mrl.common.handle.AuthAccessDeniedHandler;
import com.mrl.common.handle.AuthExceptionEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 资源访问异常处理 统一配置包括以下两个类
 * 注册 handle包中两个对于 401、403响应异常的处理类，然后再定义 EnableAuthExceptionHandler 注解 在资源服务器的启动类中增加注解启动
 * 或者在每个资源服务器中都按下面的方式注入bean，会很麻烦，而且耦合度比较高，并且统一处理的初衷便失去了意义
 * @author luc
 * @date 2020/7/2111:15
 */
public class AuthExceptionConfigure {

    //ConditionalOnMissingBean :ioc容器中没有名称为name的bean的时候就将一下注册为一个bean，好处是子模块可以自定义资源异常处理类来覆盖
    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public AuthExceptionEntryPoint authenticationEntryPoint(){
        return new AuthExceptionEntryPoint();
    }

    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public AuthAccessDeniedHandler accessDeniedHandler(){
        return new AuthAccessDeniedHandler();
    }
}
