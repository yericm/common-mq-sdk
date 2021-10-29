package org.citybrain.mq;

import org.citybrain.mq.transaction.Admin;

/**
 * @author yeric
 * @description: 消息消费者接口，用来订阅消息
 * @date 2021/10/30 03:57
 */
public interface Consumer extends Admin {
    /**
     * 订阅消息
     * @param topic 消息主题
     * @param subExpression 订阅过滤表达式字符串
     * @param listener 消息回调监听器
     */
    void subscribe(final String topic, final String subExpression, final MessageListener listener);
}
