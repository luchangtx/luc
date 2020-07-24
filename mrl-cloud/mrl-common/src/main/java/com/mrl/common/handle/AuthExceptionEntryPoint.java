package com.mrl.common.handle;

import com.mrl.common.entry.CResponse;
import com.mrl.common.entry.Constant;
import com.mrl.common.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理401类型异常
 * 资源服务器异常主要两种 1：401 令牌不正确；2：403 用户无权限
 * @author luc
 * @date 2020/7/2110:32
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException {
        CResponse cResponse=new CResponse();
        //application/json;charset=UTF-8;utf-8删除、response.setStatus(401);
        ResponseUtil.makeResponse(response, Constant.JSON_UTF8,HttpServletResponse.SC_UNAUTHORIZED,cResponse.message("token无效"));
    }
}
