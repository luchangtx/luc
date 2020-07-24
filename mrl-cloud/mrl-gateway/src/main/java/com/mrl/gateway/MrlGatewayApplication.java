package com.mrl.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy //开启zuul网关功能
@EnableDiscoveryClient //启动服务发现，类似 @EnableEurekaClient
public class MrlGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrlGatewayApplication.class, args);
    }

}
