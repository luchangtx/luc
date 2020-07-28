package com.mrl.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.mrl.common.entry.CResponse;
import com.mrl.common.entry.Constant;
import com.mrl.common.utils.ResponseUtil;
import com.mrl.gateway.properties.GatewayProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * zuul拦截器，在网关转发之前设置请求头部信息，以拦截不通过网关直接访问服务地址的请求
 * 这里只处理了请求头信息，需要再创建拦截器对各个服务进行全局拦截
 * @author luc
 * @date 2020/7/2117:02
 */
@Component
@Slf4j
public class GatewayRequestFilter extends ZuulFilter {

    @Autowired
    private GatewayProperties properties;
    private AntPathMatcher pathMatcher=new AntPathMatcher();

    //对应zuul生命周期的四个阶段：pre、post、route、error
    //我们需要在请求转发出去之前添加请求头，所以指定为pre
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    //我们要获取上下文，PreDecorationFilter 过滤器是处理上下文信息的，优先级为5，所以我们优先级设为6保证可以获取上下文
    @Override
    public int filterOrder() {
        return 6;
    }

    //true表示执行该过滤器的run方法
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取当前网关上下文
        RequestContext ctx=RequestContext.getCurrentContext();
        //获取当前请求的服务名称
        String serviceId= (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
        //获取请求对象
        HttpServletRequest request=ctx.getRequest();
        //获取请求的主机名（ip）
        String host=request.getRemoteHost();
        //获取请求方法
        String method=request.getMethod();
        //获取请求url
        String uri=request.getRequestURI();
        log.info("请求URI：{}，HTTP Method：{}，请求IP：{}，ServerId：{}",uri,method,host,serviceId);

        //禁止外部方位资源实现
        boolean shouldForward=true;
        String forbidRequestUri=properties.getForbidRequestUri();
        String[] forbidRequestUris= StringUtils.splitByWholeSeparatorPreserveAllTokens(forbidRequestUri,",");

        if (forbidRequestUris!=null&& ArrayUtils.isNotEmpty(forbidRequestUris)){
            for (String furi:forbidRequestUris){
                if (pathMatcher.match(furi,uri)){
                    shouldForward=false;
                    break;
                }
            }
        }
        if (!shouldForward){
            HttpServletResponse response=ctx.getResponse();
            CResponse cResponse=new CResponse().message("该URI不允许外部访问");
            try {
                ResponseUtil.makeResponse(response,Constant.JSON_UTF8,HttpServletResponse.SC_FORBIDDEN,cResponse);
                ctx.setSendZuulResponse(false);
                ctx.setResponse(response);
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        //请求上下文头部添加 key 和 value 的信息
        byte[] token= Base64Utils.encode((Constant.ZUUL_TOKEN_VALUE).getBytes());
        ctx.addZuulRequestHeader(Constant.ZUUL_TOKEN_HEADER,new String(token));

        return null;
    }
}
