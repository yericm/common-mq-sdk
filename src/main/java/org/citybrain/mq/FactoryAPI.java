package org.citybrain.mq;

import java.util.Properties;

/**
 * @author yeric
 * @description: MQ 各类Client构造的工程接口，用于创建Producer和Consumer
 * @date 2021/10/30 02:15
 */
public interface FactoryAPI {
    Producer createProducer(final Properties properties);

}
