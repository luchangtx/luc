package com.mrl.common.annotation;

import com.mrl.common.config.OAuth2FeignConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启feign请求拦截以处理请求头token信息
 * @author luc
 * @date 2020/7/2116:48
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(OAuth2FeignConfigure.class) //引入Feign请求拦截配置
@Deprecated
public @interface EnableOAuth2FeignClient {
}
