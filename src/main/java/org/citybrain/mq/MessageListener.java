package org.citybrain.mq;

import org.apache.rocketmq.common.message.Message;

/**
 * @author yeric
 * @description: 消息监听器，Consumer注册消息监听器来订阅消息.
 * @date 2021/10/30 03:59
 */
public interface MessageListener {
    /**
     * 消费消息接口，由应用来实现<br>
     * 网络抖动等不稳定的情形可能会带来消息重复，对重复消息敏感的业务可对消息做幂等处理
     *
     * @param message 消息
     * @param context 消费上下文
     * @return 消费结果，如果应用抛出异常或者返回Null等价于返回Action.ReconsumeLater
     * @see <a href="https://help.aliyun.com/document_detail/44397.html">如何做到消费幂等</a>
     */
    Action consume(final Message message, final ConsumeContext context);
}
