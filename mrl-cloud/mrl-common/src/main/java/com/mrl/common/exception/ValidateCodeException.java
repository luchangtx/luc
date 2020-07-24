package com.mrl.common.exception;

/**
 * 验证码异常类
 * @author luc
 * @date 2020/7/2310:56
 */
public class ValidateCodeException extends Exception {

    private static final long serialVersionUID = 8208958968762042880L;

    public ValidateCodeException(String message){
        super(message);
    }
}
