package com.mrl.auth.filter;

import com.mrl.auth.service.ValidateCodeService;
import com.mrl.common.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter 顾名思义，可以保证我们的逻辑只被执行一次
 * Filter-->GenericFilterBean-->OncePerRequestFilter
 *
 * 定义好之后，需要将该过滤器增加到spring security过滤器链中
 * @author luc
 * @date 2020/7/2313:16
 */
@Slf4j
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {
    @Autowired
    private ValidateCodeService validateCodeService;

    //处理Filter中抛出的异常 全局异常 处理不到的问题
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    /**
     * 验证码校验逻辑
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        /**
         * 当拦截的请求uri为/oauth/token,请求方法为post且参数grant_type=password的时候（对应密码模式获取令牌）需要进行验证码校验
         * doFilter,让过滤器继续往下执行，校验不通过时不继续，直接返回错误信息
         */
        RequestMatcher matcher=new AntPathRequestMatcher("/oauth/token", HttpMethod.POST.toString());
        if (matcher.matches(request)
                && StringUtils.equalsIgnoreCase(request.getParameter("grant_type"),"password")){
            try {
                validateCode(request);
                chain.doFilter(request,response);
            } catch (ValidateCodeException e) {
//                CResponse cResponse=new CResponse();
//                ResponseUtil.makeResponse(response, Constant.JSON_UTF8,HttpServletResponse.SC_INTERNAL_SERVER_ERROR,cResponse.message(e.getMessage()));
//                log.error(e.getMessage(),e);
                handlerExceptionResolver.resolveException(request,response,null,new ValidateCodeException(e.getMessage()));
            }
        }else {
            chain.doFilter(request,response);
        }
    }
    private void validateCode(HttpServletRequest request) throws ValidateCodeException {
        String code=request.getParameter("code");
        String key=request.getParameter("key");
        validateCodeService.check(key,code);
    }
}
