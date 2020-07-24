package com.mrl.gateway.filter;

import com.mrl.common.entry.CResponse;
import com.mrl.common.entry.Constant;
import com.mrl.common.utils.ResponseUtil;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * 处理zuul网关异常
 * 504：网关超时
 * 500：Internal server error
 *
 * 要想本过滤器生效需要在配置文件中增加配置，让默认的异常过滤器失效
 *
 * @author luc
 * @date 2020/7/2113:12
 */
@Slf4j
@Component
public class GatewayErrorFilter extends SendErrorFilter {

    @Override
    public Object run() {
        try {
            //获取当前网关上下文
            RequestContext ctx=RequestContext.getCurrentContext();
            //获取当前请求的服务名称
            String serviceId= (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
            //获取当前请求的异常对象
            ExceptionHolder exceptionHolder=findZuulException(ctx.getThrowable());
            //根据异常对象获取具体异常信息
            String errorCause=exceptionHolder.getErrorCause();
            Throwable throwable=exceptionHolder.getThrowable();
            String message=throwable.getMessage();
            message= StringUtils.isBlank(message)?errorCause:message;

            CResponse cResponse=resolveExceptionMessage(message,serviceId);
            HttpServletResponse response=ctx.getResponse();
            //统一处理异常返回格式 json，500状态码
            ResponseUtil.makeResponse(response, Constant.JSON_UTF8,HttpServletResponse.SC_INTERNAL_SERVER_ERROR,cResponse);
            log.error("Zuul sendError：{}",cResponse.getMessage());

        }catch (Exception e){
            log.error("Zuul sendError",e);
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }

    /**
     * 处理异常消息
     * @param message
     * @param serviceId
     * @return
     */
    private CResponse resolveExceptionMessage(String message,String serviceId){
        CResponse cResponse=new CResponse();
        if (StringUtils.containsIgnoreCase(message,"time out")){
            return cResponse.message("请求"+serviceId+"服务超时");
        }
        if (StringUtils.containsIgnoreCase(message,"forwarding error")){
            return cResponse.message(serviceId+"服务不可用");
        }
        return cResponse.message("Zuul请求"+serviceId+"服务异常");
    }
}
