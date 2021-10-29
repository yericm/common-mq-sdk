package org.citybrain.mq.api.impl;

import org.citybrain.mq.Consumer;
import org.citybrain.mq.MessageListener;
import org.citybrain.mq.exception.ClientException;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yeric
 * @description:
 * @date 2021/10/30 04:05
 */
public class ConsumerImpl extends ClientAbstract implements Consumer {
    private final ConcurrentHashMap<String, MessageListener> subscribeTable = new ConcurrentHashMap<String, MessageListener>();

    public ConsumerImpl(Properties properties) {
        super(properties);
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

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void subscribe(String topic, String subExpression, MessageListener listener) {
        if (null == topic) {
            throw new ClientException("topic is null");
        }

        if (null == listener) {
            throw new ClientException("listener is null");
        }
        this.subscribeTable.put(topic, listener);
        super.subscribe(topic, subExpression);
    }
}
