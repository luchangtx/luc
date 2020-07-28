package com.mrl.server.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author luc
 * @date 2020/7/2018:34
 */
@RestController
@Slf4j
public class SystemController {
    @Value("${spring.application.name}")
    private String serviceId;

    //获取服务名称
    @GetMapping("info")
    public String getInfo(){
        return serviceId;
    }

    //获取当前登录的用户信息
    @GetMapping("currentUser")
    public Principal currentUser(Principal principal){
        return principal;
    }

    //测试 feign 微服务之间的调用
    @GetMapping("feign")
    public String feign(String name){
        log.info("/feign服务被调用");
        return serviceId+":"+name;
    }
}
