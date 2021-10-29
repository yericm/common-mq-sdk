package org.citybrain.mq.exception;

/**
 * @author yeric
 * @description: 统一对外暴露的客户端异常接口
 * @date 2021/10/30 02:29
 */
public class ClientException extends RuntimeException{
    private static final long serialVersionUID = 5755356574640041094L;

    /**
     * 默认异常构造函数.
     */
    public ClientException() {
    }

    /**
     * 异常接口构造函数
     *
     * @param message 需要向外传递的异常信息
     */
    public ClientException(String message) {
        super(message);
    }

    /**
     * 异常接口构造函数
     *
     * @param cause 需要向外传递的异常
     */
    public ClientException(Throwable cause) {
        super(cause);
    }

    /**
     * 异常接口构造函数
     *
     * @param message 需要向外传递的异常信息
     * @param cause 需要向外传递的异常
     */
    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public static String errorMessage(final String msg, final String url) {
        return String.format("%s\nSee %s for further details.", msg, url);
    }
}
