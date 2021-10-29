package org.citybrain.mq;

import java.util.Properties;

/**
 * @author yeric
 * @description:
 * @date 2021/10/30 02:09
 */
public class Factory {

    /**
     * 工厂实现类实例. 单例模式.
     */
    private static FactoryAPI factory = null;

    static {
        try {
            // ons client 优先加载
            Class<?> factoryClass =
                    Factory.class.getClassLoader().loadClass(
                            "com.aliyun.openservices.ons.api.impl.ONSFactoryNotifyAndMetaQImpl");
            factory = (FactoryAPI) factoryClass.newInstance();
        } catch (Throwable e) {
            try {
                Class<?> factoryClass =
                        Factory.class.getClassLoader().loadClass(
                                "org.citybrain.mq.api.impl.FactoryImpl");
                factory = (FactoryAPI) factoryClass.newInstance();
            } catch (Throwable e1) {
                e.printStackTrace();
                e1.printStackTrace();
            }
        }
    }

    public static Producer createProducer(final Properties properties) {
        return factory.createProducer(properties);
    }

    public static Consumer createConsumer(final Properties properties) {
        return onsFactory.createConsumer(properties);
    }
}
