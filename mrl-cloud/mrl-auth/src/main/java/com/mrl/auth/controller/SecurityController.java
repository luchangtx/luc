package com.mrl.auth.controller;

import com.mrl.auth.service.ValidateCodeService;
import com.mrl.common.entry.CResponse;
import com.mrl.common.exception.BaseException;
import com.mrl.common.exception.ErrorType;
import com.mrl.common.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author luc
 * @date 2020/7/2013:38
 */
@RestController
public class SecurityController {
    @Autowired
    private ConsumerTokenServices consumerTokenServices;
    @Autowired
    private ValidateCodeService validateCodeService;

    /**
     * 测试oauth开头的请求携带token是否可以访问
     * 不可以，不受资源服务配置管理，使用令牌也不能获取资源
     * @return
     */
    @GetMapping("oauth/test")
    public String oauth(){
        return "oauth";
    }

    //获取当前用户
    @GetMapping("user")
    public Principal currentUser(Principal principal){
        return principal;
    }

    @GetMapping("get")
    public String get() throws BaseException {
        throw new BaseException(ErrorType.SYSTEM_ERROR,"全局异常测试");
    }

    //通过 ConsumerTokenServices 注销token
    @DeleteMapping("signout")
    public CResponse signout(HttpServletRequest request) throws Exception {
        CResponse result=new CResponse();
        String authorization=request.getHeader("Authorization");
        String token= StringUtils.replace(authorization,"Bearer ","");
        if (!consumerTokenServices.revokeToken(token)){
            throw new Exception("退出登录失败！");
        }
        result.data(token);
        return result;
    }

    /**
     * @Description: 生成图形验证码
     * @author luc
     * @date 2020/7/23 11:24
     */
    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws ValidateCodeException, IOException {
        validateCodeService.create(request,response);
    }

}
