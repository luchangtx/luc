package com.mrl.common.config;

import com.mrl.common.entry.Constant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.Base64Utils;

/**
 * 需要配套一个注解类，用于@Enable驱动启动
 * @author luc
 * @date 2020/7/2116:30
 */
public class OAuth2FeignConfigure {
    //注册一个拦截器，收到设置请求头token数据
    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(){
        return requestTemplate -> {

            //因为增加了网关拦截，需要提供zuul token才可以访问资源，所以需要手动设置zuul token
            //而此处一定是通过了网关拦截器，验证了zuul token 的正确性之后
            String zuulToken=new String(Base64Utils.encode(Constant.ZUUL_TOKEN_VALUE.getBytes()));
            requestTemplate.header(Constant.ZUUL_TOKEN_HEADER,zuulToken);

            Object details= SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof OAuth2AuthenticationDetails){
                String token=((OAuth2AuthenticationDetails) details).getTokenValue();
                //请求模板对象设置token值
                requestTemplate.header(HttpHeaders.AUTHORIZATION,"Bearer "+token);
            }
        };
    }
    //因为我们开启了回退熔断机制，还需要增加配置，将SecurityContext对象从主线程传输到Hystrix线程
}
