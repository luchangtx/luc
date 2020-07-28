package com.mrl.monitor.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer //开启SBA 服务端功能
public class MrlMonitorAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrlMonitorAdminApplication.class, args);
    }

}
