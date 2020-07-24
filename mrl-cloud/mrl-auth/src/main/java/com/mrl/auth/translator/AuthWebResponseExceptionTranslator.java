package com.mrl.auth.translator;

import com.mrl.common.entry.CResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * 权限认证异常翻译处理类
 * @author luc
 * @date 2020/7/2110:05
 */
@Slf4j
@Component
public class AuthWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        //统一返回500状态码
        ResponseEntity.BodyBuilder status=ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);

        CResponse response=new CResponse();
        String message="认证失败";
        log.error(message,e);
        if (e instanceof UnsupportedGrantTypeException){
            message="不支持该认证类型";
            return status.body(response.message(message));
        }
        if (e instanceof InvalidGrantException){
           if (StringUtils.containsIgnoreCase(e.getMessage(),"Invalid refresh token")){
               message="refresh token无效";
               return status.body(response.message(message));
           }
           if (StringUtils.containsIgnoreCase(e.getMessage(),"locked")){
               message="用户已被锁定，请联系管理元";
               return status.body(response.message(message));
           }
           message="用户名或密码错误";
            return status.body(response.message(message));
        }
        return status.body(response.message(message));
    }
}
