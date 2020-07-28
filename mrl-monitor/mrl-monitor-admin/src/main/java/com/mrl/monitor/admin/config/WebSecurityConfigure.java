package com.mrl.monitor.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * web安全配置类
 * @author luc
 * @date 2020/7/2715:18
 */
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    private String adminContextPath;

    public WebSecurityConfigure(AdminServerProperties adminServerProperties){
        this.adminContextPath=adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler=new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        http.authorizeRequests()
                 //免认证路径
                .antMatchers(adminContextPath+"/assets/**").permitAll()
                .antMatchers(adminContextPath+"/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(adminContextPath+"/login").successHandler(successHandler)
                .and()
                .logout().logoutUrl(adminContextPath+"/logout")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
