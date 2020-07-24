package com.mrl.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mrl.common.entry.CResponse;
import com.mrl.common.entry.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 该拦截器可以拦截所有web请求，在preHandle中获取zuul token校验正确性，不通过时返回403
 * 要让该拦截器生效，需要定义一个配置类将其注册到ioc容器
 * @author luc
 * @date 2020/7/2117:23
 */
@Slf4j
public class ServerProtectInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //从请求头获取 zuul token
        String token=request.getHeader(Constant.ZUUL_TOKEN_HEADER);
        String zuulToken=new String(Base64Utils.encode(Constant.ZUUL_TOKEN_VALUE.getBytes()));
        if (StringUtils.equals(zuulToken,token)){
            return true;
        }else {
            log.error("尝试不通过网关获取资源：{}");
            CResponse cResponse=new CResponse();
            response.setContentType(Constant.JSON_UTF8);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.getWriter().write(JSONObject.toJSONString(cResponse.message("请通过网关获取资源")));
            response.getOutputStream().write(JSONObject.toJSONString(cResponse.message("请通过网关获取资源")).getBytes());
            return false;
        }
    }
}
