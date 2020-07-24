package com.mrl.common.exception;

/**
 * @author luc
 * @date 2020/7/2110:13
 */
public class BaseException extends RuntimeException {

    /**
     * 异常对应的错误类型
     */
    private ErrorType errorType;

    /**
     * 默认是系统异常
     */
    public BaseException() {
        this.errorType = ErrorType.SYSTEM_ERROR;
    }

    public BaseException(ErrorType errorType) {
        this.errorType = errorType;
    }
    public BaseException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }
    public BaseException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }
}
