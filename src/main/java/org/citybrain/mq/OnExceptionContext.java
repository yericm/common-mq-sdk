package org.citybrain.mq;

import org.citybrain.mq.exception.ClientException;

/**
 * @author yeric
 * @description: 发送消息异常上下文
 * @date 2021/10/30 02:28
 */
public class OnExceptionContext {

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 消息主题
     */
    private String topic;

    /**
     * 异常对象, 包含详细的栈信息
     */
    private ClientException exception;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ClientException getException() {
        return exception;
    }

    public void setException(ClientException exception) {
        this.exception = exception;
    }
}
