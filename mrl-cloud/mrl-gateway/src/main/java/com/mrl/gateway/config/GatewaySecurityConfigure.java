package com.mrl.gateway.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author luc
 * @date 2020/7/2017:39
 */
@EnableWebSecurity
public class GatewaySecurityConfigure extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //引入了common模块，而common引入了Spring cloud Security，所以我们需要定义一个自己的WebSecurity配置来覆盖默认的
        //这里主要时用来关闭csrf功能，否则会报csrf相关异常
        http.csrf().disable();
    }
}
