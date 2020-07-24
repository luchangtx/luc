package com.mrl.auth;

import com.mrl.common.annotation.EnableAuthExceptionHandler;
import com.mrl.common.annotation.EnableLettuceRedis;
import com.mrl.common.annotation.EnableServerProtect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableAuthExceptionHandler //启动资源访问异常处理(401 403)
@EnableServerProtect //启动网关拦截
@EnableLettuceRedis //启动lettuce redis配置
@MapperScan("com.mrl.auth.mapper") //将Mapper类注入IOC容器
public class MrlAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrlAuthApplication.class, args);
    }

}
