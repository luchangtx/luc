package com.mrl.server.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * 读取swagger 魔法值配置
 * @author luc
 * @date 2020/7/2619:05
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:server-system.properties"})
@ConfigurationProperties(prefix = "mrl.server.system")
public class ServerSystemProperties {

    /**
     * 免认证uri，多个值用逗号隔开
     */
    private String anonUrl;

    private SwaggerProperties swagger=new SwaggerProperties();
}
