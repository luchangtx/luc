package com.mrl.gateway.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author luc
 * @date 2020/7/2716:51
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:gateway.properties"})
@ConfigurationProperties(prefix = "mrl.gateway")
public class GatewayProperties {

    /**
     * 禁止外部访问的URI，多个值逗号隔开
     */
    private String forbidRequestUri;
}
