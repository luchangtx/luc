package com.mrl.common.annotation;

import com.mrl.common.config.AuthExceptionConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动资源访问异常处理(401 403)
 * @author luc
 * @date 2020/7/2111:20
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AuthExceptionConfigure.class) //我们将配置类引入进来
public @interface EnableAuthExceptionHandler {
    /**
     * AuthExceptionEntryPoint+AuthAccessDeniedHandler
     * common模块是一个普通的maven项目（没有启动类），所以即使在两个类上使用 @Component，也无法注册到各个微服务的ioc容器中
     * 所以我们可以自定义注解，使用@Enable模块驱动方式来解决
     */
}
