package com.mrl.common.handle;

import com.mrl.common.entry.CResponse;
import com.mrl.common.entry.Constant;
import com.mrl.common.utils.ResponseUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理403类型异常
 * @author luc
 * @date 2020/7/2111:11
 */
public class AuthAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException {
        CResponse cResponse=new CResponse();
        ResponseUtil.makeResponse(response, Constant.JSON_UTF8,HttpServletResponse.SC_FORBIDDEN,cResponse.message("没有权限访问资源"));
    }
}
