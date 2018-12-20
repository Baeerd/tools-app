package com.app.common.exception;

/**
 * 信息提示异常，用于代码中直接抛出给前台
 * MessageInterceptor对这个异常进行了处理
 */
public class MessageException extends RuntimeException {

    public MessageException(String message) {
        super(message);
    }

    public MessageException() {
        super();
    }
}
