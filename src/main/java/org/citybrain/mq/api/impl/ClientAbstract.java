package org.citybrain.mq.api.impl;

import org.citybrain.mq.PropertyKeyConst;
import org.citybrain.mq.transaction.Admin;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author yeric
 * @description:
 * @date 2021/10/30 02:38
 */
public abstract class ClientAbstract implements Admin {
    protected final Properties properties;
    protected final AtomicBoolean started = new AtomicBoolean(false);



    public ClientAbstract(Properties properties) {
        this.properties = properties;
    }

}
