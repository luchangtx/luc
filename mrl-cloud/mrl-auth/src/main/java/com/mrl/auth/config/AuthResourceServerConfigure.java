package com.mrl.auth.config;

import com.mrl.common.handle.AuthAccessDeniedHandler;
import com.mrl.common.handle.AuthExceptionEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器配置类
 * 主要用于资源保护，客户端需要根据Oauth2协议方法的令牌来请求受保护的资源
 * 虽然该模块是认证服务器，但该服务本身也可以对外提供REST服务，比如通过token获取当前登录用户信息，注销token
 * 所以他是一台资源服务器，我们需要定义一个资源服务器的配置类AuthResourceServerConfigure
 * @author luc
 * @date 2020/7/1718:40
 */
@Configuration
@EnableResourceServer
public class AuthResourceServerConfigure extends ResourceServerConfigurerAdapter {
    @Autowired
    private AuthExceptionEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthConfigure authConfigure;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //该安全配置对所有请求都生效
        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                //设置免认证路径
                .antMatchers(authConfigure.getAnonUrl()).permitAll()
                .antMatchers("/**").authenticated()
                .and().httpBasic();
    }

    //通过注解已在启动类注入401 403异常的处理，在此处资源服务配置中引入
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
