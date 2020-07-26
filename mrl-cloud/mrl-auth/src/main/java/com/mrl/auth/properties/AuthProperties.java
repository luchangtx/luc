package com.mrl.auth.properties;

import com.mrl.auth.properties.ClientsProperties;
import com.mrl.auth.properties.ValidateCodeProperties;
import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author luc
 * @date 2020/7/2019:08
 */
@Data
@SpringBootConfiguration //Component派生注解，用于将本类纳入到ioc容器
@PropertySource(value = {"classpath:auth.properties"})
@ConfigurationProperties(prefix = "mrl.auth")
public class AuthProperties {
    //oauth认证客户端配置
    private ClientsProperties[] clients={};
    private int accessTokenValiditySeconds = 60 * 60 * 24;
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

    /**
     * 免认证路径
     * 除了/oauth开头的请求都受资源服务器配置类AuthResourceServerConfigure的保护
     * 图形验证码在用户登录前，尚未获取token，所以需要设置免认证
     */
    private String anonUrl;

    //验证码配置
    private ValidateCodeProperties code=new ValidateCodeProperties();
}
