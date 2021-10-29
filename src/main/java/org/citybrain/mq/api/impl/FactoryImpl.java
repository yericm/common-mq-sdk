package org.citybrain.mq.api.impl;

import org.citybrain.mq.FactoryAPI;
import org.citybrain.mq.Producer;
import org.citybrain.mq.util.ONSUtil;

import java.util.Properties;

/**
 * @author yeric
 * @description:
 * @date 2021/10/30 02:34
 */
public class FactoryImpl implements FactoryAPI {
    @Override
    public Producer createProducer(Properties properties) {
        return new ProducerImpl(ONSUtil.extractProperties(properties));
    }
}
