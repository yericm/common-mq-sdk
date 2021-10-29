package org.citybrain.mq;

import org.apache.rocketmq.common.message.Message;

import java.util.Date;
import java.util.Properties;

/**
 * @author yeric
 * @description:
 * @date 2021/10/30 03:31
 */
public class SimpleMQProducer {
    public static void main(String[] args) {
        Properties producerProperties = new Properties();
        producerProperties.setProperty(PropertyKeyConst.GROUP_ID, "xxxx-group");
        producerProperties.setProperty(PropertyKeyConst.NAMESRV_ADDR, "127.0.0.1:9876");
        Producer producer = Factory.createProducer(producerProperties);
        producer.start();
        System.out.println("Producer Started");
        String MqConfig_TOPIC = "xxx-topic";
        for (int i = 0; i < 10; i++) {
            Message message = new Message(MqConfig_TOPIC, "xxx-tag", "mq send transaction message test".getBytes());
            try {
                SendResult sendResult = producer.send(message);
                assert sendResult != null;
                System.out.println(new Date() + " Send mq message success! Topic is:" + MqConfig_TOPIC + " msgId is: " + sendResult.getMessageId());
            } catch (ClassCastException e) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                System.out.println(new Date() + " Send mq message failed! Topic is:" + MqConfig_TOPIC);
                e.printStackTrace();
            }
        }
    }
}
