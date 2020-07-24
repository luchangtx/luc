package com.mrl.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MrlRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrlRegisterApplication.class, args);
    }

}
