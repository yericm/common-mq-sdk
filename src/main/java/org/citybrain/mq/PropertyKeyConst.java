package org.citybrain.mq;

/**
 * @author yeric
 * @description:
 * @date 2021/10/30 02:42
 */
public class PropertyKeyConst {

    /**
     * Group ID，客户端ID
     */
    public static final String GROUP_ID = "GROUP_ID";
    /**
     * Name Server地址
     */
    public static final String NAMESRV_ADDR = "NAMESRV_ADDR";

    /**
     * 消息发送超时时间，如果服务端在配置的对应时间内未ACK，则发送客户端认为该消息发送失败。
     */
    public static final String SendMsgTimeoutMillis = "SendMsgTimeoutMillis";

    /**
     * 是否开启mqtransaction，用于使用exactly-once投递语义
     */
    public static final String EXACTLYONCE_DELIVERY = "exactlyOnceDelivery";
    /**
     * MQ消息轨迹开关
     */
    public static final String MsgTraceSwitch = "MsgTraceSwitch";
}
