package com.mrl.register.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author luc
 * @date 2020/7/1715:52
 */
@EnableWebSecurity
public class RegisterSecurityConfigure extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * spring security4.0之后默认会启用csrf保护（跨站请求伪造攻击）
         * 要求用户向应用程序发送请求时都必须携带一个有效的csrf令牌
         * 而eureka client端通常不会拥有有效的跨站点请求令牌，此时eureka server端应该对eureka请求路径放行
         */
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }
}
