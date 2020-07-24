package com.mrl.common.annotation;

import com.mrl.common.selector.CloudApplicationSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启自定义配置的功能
 * @author luc
 * @date 2020/7/2118:26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CloudApplicationSelector.class) //引入微服务必备的自定义配置
public @interface AuthCloudApplication {
}
