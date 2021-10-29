package org.citybrain.mq.api.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.protocol.ResponseCode;
import org.apache.rocketmq.remoting.exception.RemotingConnectException;
import org.apache.rocketmq.remoting.exception.RemotingTimeoutException;
import org.citybrain.mq.*;
import org.citybrain.mq.exception.ClientException;

import java.util.Properties;
import java.util.concurrent.ExecutorService;

/**
 * @author yeric
 * @description:
 * @date 2021/10/30 02:35
 */
public class ProducerImpl extends ClientAbstract implements Producer {

    private final DefaultMQProducer defaultMQProducer;

    public ProducerImpl(final Properties properties) {
        super(properties);

        String producerGroup = properties.getProperty(PropertyKeyConst.GROUP_ID, properties.getProperty(PropertyKeyConst.GROUP_ID));
        if (StringUtils.isEmpty(producerGroup)) {
            producerGroup = "__ONS_PRODUCER_DEFAULT_GROUP";
        }

        this.defaultMQProducer =
                new DefaultMQProducer(producerGroup);


        if (properties.containsKey(PropertyKeyConst.SendMsgTimeoutMillis)) {
            this.defaultMQProducer.setSendMsgTimeout(Integer.valueOf(properties.get(PropertyKeyConst.SendMsgTimeoutMillis).toString()));
        } else {
            this.defaultMQProducer.setSendMsgTimeout(5000);
        }


        this.defaultMQProducer.setNamesrvAddr(properties.getProperty(PropertyKeyConst.NAMESRV_ADDR));
        // 消息最大大小4M
        this.defaultMQProducer.setMaxMessageSize(1024 * 1024 * 4);
    }


    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void start() {
        try {
            if (this.started.compareAndSet(false, true)) {
                this.defaultMQProducer.start();
            }
        } catch (Exception e) {
            throw new ClientException(e.getMessage());
        }
    }

    @Override
    public void shutdown() {
        if (this.started.compareAndSet(true, false)) {
            this.defaultMQProducer.shutdown();
        }
    }

    @Override
    public SendResult send(Message message) {

        try {
            org.apache.rocketmq.client.producer.SendResult send = this.defaultMQProducer.send(message);
//            message.setMsgID(sendResultRMQ.getMsgId());
            SendResult sendResult = new SendResult();
            sendResult.setMessageId(send.getMsgId());
            sendResult.setTopic(message.getTopic());
//            sendResult.setTopic(sendResultRMQ.getMessageQueue().getTopic());
//            sendResult.setMessageId(sendResultRMQ.getMsgId());
            return sendResult;
        } catch (Exception e) {
//            LOGGER.error(String.format("Send message Exception, %s", message), e);
            throw checkProducerException(message.getTopic(), message.getTopic(), e);
        }
    }

    @Override
    public void sendOneway(Message message) {

    }

    @Override
    public void sendAsync(Message message, SendCallback sendCallback) {

    }

    @Override
    public void setCallbackExecutor(ExecutorService callbackExecutor) {

    }

    private ClientException checkProducerException(String topic, String msgId, Throwable e) {
        if (e instanceof MQClientException) {
            //
            if (e.getCause() != null) {
                // 无法连接Broker
                if (e.getCause() instanceof RemotingConnectException) {
                    return new ClientException(
                            ClientException.errorMessage(String.format("Connect broker failed, Topic=%s, msgId=%s", topic, msgId), "connect_broker_failed"));
                }
                // 发送消息超时
                else if (e.getCause() instanceof RemotingTimeoutException) {
                    return new ClientException(ClientException.errorMessage(String.format("Send message to broker timeout, %dms, Topic=%s, msgId=%s",
                            this.defaultMQProducer.getSendMsgTimeout(), topic, msgId), "send_msg_failed"));
                }
                // Broker返回异常
                else if (e.getCause() instanceof MQBrokerException) {
                    MQBrokerException excep = (MQBrokerException) e.getCause();
                    return new ClientException(ClientException.errorMessage(
                            String.format("Receive a broker exception, Topi=%s, msgId=%s, %s", topic, msgId, excep.getErrorMessage()),
                            "broker_response_exception"));
                }
            }
            // 纯客户端异常
            else {
                MQClientException excep = (MQClientException) e;
                if (-1 == excep.getResponseCode()) {
                    return new ClientException(
                            ClientException.errorMessage(String.format("Topic does not exist, Topic=%s, msgId=%s", topic, msgId), "topic_not_exist"));
                } else if (ResponseCode.MESSAGE_ILLEGAL == excep.getResponseCode()) {
                    return new ClientException(
                            ClientException.errorMessage(String.format("ONS Client check message exception, Topic=%s, msgId=%s", topic, msgId),
                                    "msg_check_failed"));
                }
            }
        }

        return new ClientException("defaultMQProducer send exception", e);
    }

}
