package com.sy.exception;

/**
 * 自定义异常
 *
 * @author lfeiyang
 * @since 2022-05-04 22:50
 */
public class BaseException extends RuntimeException {
    public BaseException() {
        super();
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}
