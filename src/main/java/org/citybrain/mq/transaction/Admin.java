package org.citybrain.mq.transaction;

/**
 * @author yeric
 * @description: 管理类接口
 * @date 2021/10/30 02:17
 */
public interface Admin {
    /**
     * 检查服务是否已经启动.
     * Returns:
     * true如果服务已启动; 其它情况返回false
     * See Also:
     * isClosed()
     * @return
     */
    boolean isStarted();

    /**
     * 检查服务是否已经关闭
     * Returns:
     * true如果服务已关闭; 其它情况返回false
     * See Also:
     * isStarted()
     * @return
     */
    boolean isClosed();

    /**
     * 启动服务
     */
    void start();
    // 关闭服务
    void shutdown();
}
