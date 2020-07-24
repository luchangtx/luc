package com.mrl.common.annotation;

import com.mrl.common.config.ServerProtectConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * //启动网关拦截
 * @author luc
 * @date 2020/7/2117:41
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ServerProtectConfigure.class) //引入网关拦截器配置以驱动
public @interface EnableServerProtect {
}
