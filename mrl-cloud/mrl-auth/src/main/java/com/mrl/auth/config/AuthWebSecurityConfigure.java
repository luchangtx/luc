package com.mrl.auth.config;

import com.mrl.auth.filter.ValidateCodeFilter;
import com.mrl.auth.service.AuthUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * web相关的安全配置类
 * spring cloud oauth2内部定义的 获取令牌、刷新令牌 都是以/oauth/开头的，所以此类处理和令牌相关的请求
 * @author luc
 * @date 2020/7/1718:21
 */
@Order(2) //解释看port.txt
@EnableWebSecurity
public class AuthWebSecurityConfigure extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthUserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder; //在mrl-commom模块中注册

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    //oauth的密码认证模式需要AuthenticationManager,注意方法名是有 Bean 结尾的
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //将 ValidateCodeFilter 增加到 UsernamePasswordAuthenticationFilter 前
        //即先校验验证码，再校验用户名密码
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //安全配置类只对 /oauth/开头的请求有效
                .requestMatchers()
                .antMatchers("/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }
    //虽然该模块是认证服务器，但该服务本身也可以对外提供REST服务，比如通过token获取当前登录用户信息，注销token
    //所以他是一台资源服务器，我们需要定义一个资源服务器的配置类AuthResourceServerConfigure
}
