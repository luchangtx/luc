package com.mrl.auth.config;

import com.mrl.auth.properties.ClientsProperties;
import com.mrl.auth.service.AuthUserDetailService;
import com.mrl.auth.translator.AuthWebResponseExceptionTranslator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 认证服务器相关的安全配置
 * @author luc
 * @date 2020/7/2010:57
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfigure extends AuthorizationServerConfigurerAdapter {
    //web安全配置类 AuthWebSecurityConfigure 中定义的 AuthenticationManager 注入
    @Autowired
    private AuthenticationManager authenticationManager;

    //mrl-auth模块依赖mrl-common,后者增加了redis依赖，所以mrl-auth的上下文中已经装配好了redis相关配置（默认配置，建议在yml文件中配置）
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private AuthUserDetailService userDetailService;

    //web安全配置类 AuthWebSecurityConfigure 中定义的 PasswordEncoder 注入
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthConfigure authConfigure;

    //异常翻译处理类
    @Autowired
    private AuthWebResponseExceptionTranslator exceptionTranslator;

    //如果需要多个client（一个密码认证，一个授权码认证），可以继续使用withClient
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //客户端 使用密码认证模式并设置登录的账号密码
        //获取配置的client信息
        ClientsProperties[] clientsArray=authConfigure.getClients();
        InMemoryClientDetailsServiceBuilder builder=clients.inMemory();

        if (ArrayUtils.isNotEmpty(clientsArray)){
            for (ClientsProperties client:clientsArray){
                if (StringUtils.isBlank(client.getClient())){
                    throw new Exception("client不能为空");
                }
                if (StringUtils.isBlank(client.getSecret())){
                    throw new Exception("secret不能为空");
                }
                String[] grantTypes=StringUtils.splitByWholeSeparatorPreserveAllTokens(client.getGrantType(),",");
                builder.withClient(client.getClient())
                        //客户端获取令牌的时候必须使用client_id=mrl，client_secret=luc的标识
                        .secret(passwordEncoder.encode(client.getSecret()))
                        //该client_id支持密码模式获取令牌，并可以通过refresh_token获取新令牌
                        .authorizedGrantTypes(grantTypes)
                        //在获取client_id=mrl的令牌时，scope只能指定为all
                        .scopes(client.getScope());
            }
        }
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager)
                .tokenServices(defaultTokenServices())
                //指定异常翻译处理类
                .exceptionTranslator(exceptionTranslator);
    }

    //Method annotated with @Bean is called directly. Use dependency injection instead,加上@Configuration 注解
    //认证服务器生成的token存储到redis
    @Bean(name = "tokenStore")
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    //指定令牌的基本配置
    @Primary
    @Bean(name = "defaultTokenServices")
    @DependsOn("tokenStore")
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices=new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        //开启刷新令牌的支持
        tokenServices.setSupportRefreshToken(true);
        //指定令牌有效时间 一天
        tokenServices.setAccessTokenValiditySeconds(authConfigure.getAccessTokenValiditySeconds());
        //刷新令牌有效时间 七天
        tokenServices.setRefreshTokenValiditySeconds(authConfigure.getRefreshTokenValiditySeconds());
        return tokenServices;
    }
}
