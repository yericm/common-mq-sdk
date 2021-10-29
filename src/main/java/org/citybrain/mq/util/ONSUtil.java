package org.citybrain.mq.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

/**
 * @author yeric
 * @description:
 * @date 2021/10/30 03:05
 */
public class ONSUtil {
    public static Properties extractProperties(final Properties properties) {
        Properties newPro = new Properties();
        Properties inner = null;
        try {
            Field field = Properties.class.getDeclaredField("defaults");
            field.setAccessible(true);
            inner = (Properties) field.get(properties);
        } catch (Exception ignore) {
        }

        if (inner != null) {
            for (final Map.Entry<Object, Object> entry : inner.entrySet()) {
                newPro.setProperty(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }

        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            newPro.setProperty(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }

        return newPro;
    }
}
