package com.mrl.server.normal;

import com.mrl.common.annotation.AuthCloudApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableFeignClients //开启Feign Client功能
@SpringBootApplication
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解
@AuthCloudApplication //开启资源访问异常处理(401 403)、开启feign请求拦截以处理请求头token信息、启动网关拦截
public class MrlServerNormalApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrlServerNormalApplication.class, args);
    }

}
