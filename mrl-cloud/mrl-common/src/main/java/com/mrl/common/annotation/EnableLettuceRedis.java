package com.mrl.common.annotation;

import com.mrl.common.config.LettuceRedisConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Enable 驱动Lettuce Redis
 * @author luc
 * @date 2020/7/2310:14
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LettuceRedisConfigure.class) //引入redis自定义配置类
public @interface EnableLettuceRedis {
}
