package com.mrl.server.system;

import com.mrl.common.annotation.AuthCloudApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启spring-cloud-security权限注解
@AuthCloudApplication //开启资源访问异常处理(401 403)、开启feign请求拦截以处理请求头token信息、启动网关拦截
@MapperScan("com.mrl.server.system.mapper") //自动扫面mapper
public class MrlServerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrlServerSystemApplication.class, args);
    }

}
