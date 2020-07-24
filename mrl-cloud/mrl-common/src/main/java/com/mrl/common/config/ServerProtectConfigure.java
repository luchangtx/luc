package com.mrl.common.config;

import com.mrl.common.interceptor.ServerProtectInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 要让本类生效需要定义@Enable注解来驱动
 * @author luc
 * @date 2020/7/2117:34
 */
@Configuration
public class ServerProtectConfigure implements WebMvcConfigurer {

    //定义了几个和密码加密校验相关的方法，我们返回spring security内部实现好的BCryptPasswordEncoder
    //BCryptPasswordEncoder特点是对于相同的密码，没磁加密出来的加密串都不同
    //ConditionalOnMissingBean 子类可以定义自己的，当ioc容器没有该类型的bean注入

    /**
     * 原本在 AuthWebSecurityConfigure 中注册bean，但是此bean可能在多个服务中都用到，
     * 看名字都是跟web相关的配置，放在这里配置正好合适
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //注册ServerProtectInterceptor
    @Bean
    public HandlerInterceptor serverProtectInterceptor(){
        return new ServerProtectInterceptor();
    }

    //将注册的bean加入到spring的拦截器链中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serverProtectInterceptor());
    }
}
