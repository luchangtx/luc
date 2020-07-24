package com.mrl.auth.service;

import com.mrl.auth.config.AuthConfigure;
import com.mrl.auth.properties.ValidateCodeProperties;
import com.mrl.common.entry.Constant;
import com.mrl.common.exception.ValidateCodeException;
import com.mrl.common.service.RedisService;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author luc
 * @date 2020/7/2310:58
 */
@Service
public class ValidateCodeService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private AuthConfigure configure;

    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws ValidateCodeException
     * @throws IOException
     */
    public void create(HttpServletRequest request, HttpServletResponse response) throws ValidateCodeException, IOException {
        /**
         * 前后端不分离的架构，浏览器传输jsessionid来与验证码一一对应
         * 前后端分离，客户端发送请求不再携带jsessionid（不再基于session），所以我们需要客户端按一定算法随机生成key模拟
         */
        String key=request.getParameter("key");
        if (StringUtils.isBlank(key))
            throw new ValidateCodeException("验证码key不能为空");
        ValidateCodeProperties code=configure.getCode();
        setHeader(response,code.getType());

        Captcha captcha=createCaptcha(code);
        redisService.set(Constant.CODE_PREFIX+key,StringUtils.lowerCase(captcha.text()),code.getTime());
        captcha.out(response.getOutputStream());
    }

    /**
     * 校验验证码
     * @param key 前端上送 key
     * @param value 前端上送待校验值
     * @throws ValidateCodeException
     */
    public void check(String key,String value) throws ValidateCodeException {
        Object codeInRedis=redisService.get(Constant.CODE_PREFIX+key);
        if (StringUtils.isBlank(value))
            throw new ValidateCodeException("请输入验证码");
        if (codeInRedis==null)
            throw new ValidateCodeException("验证码已过期");
        if (!StringUtils.equalsIgnoreCase(value,String.valueOf(codeInRedis)))
            throw new ValidateCodeException("验证码不正确");
    }

    /**
     * 根据配置生成验证码
     * @param code
     * @return
     */
    private Captcha createCaptcha(ValidateCodeProperties code){
        Captcha captcha;
        if (StringUtils.equalsIgnoreCase(code.getType(),Constant.GIF)){
            captcha=new GifCaptcha(code.getWidth(),code.getHeight(),code.getLength());
        }else {
            captcha=new SpecCaptcha(code.getWidth(),code.getHeight(),code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }

    /**
     * 设置响应头信息（类型等）
     * @param response
     * @param type
     */
    private void setHeader(HttpServletResponse response,String type){
        if (StringUtils.equals(type, Constant.GIF)){
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        }else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA,"No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL,"no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES,0L);
    }
}
