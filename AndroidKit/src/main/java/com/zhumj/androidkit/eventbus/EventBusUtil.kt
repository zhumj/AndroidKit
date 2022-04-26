package com.zhumj.androidkit.eventbus

import org.greenrobot.eventbus.EventBus

/**
 * @author Created by zhumj
 * @date 2022/4/23
 * @description EventBus 工具类
 */
object EventBusUtil {
    /**
     * 注册 EventBus
     * @param subscriber
     */
    fun register(subscriber: Any) {
        val eventBus = EventBus.getDefault()
        if (!eventBus.isRegistered(subscriber)) {
            eventBus.register(subscriber)
        }
    }

    /**
     * 解除注册 EventBus
     * @param subscriber
     */
    fun unregister(subscriber: Any) {
        val eventBus = EventBus.getDefault()
        if (eventBus.isRegistered(subscriber)) {
            eventBus.unregister(subscriber)
        }
    }

    /**
     * 发送事件消息
     * @param event
     */
    fun <T> post(event: EventMessage<T>) {
        EventBus.getDefault().post(event)
    }

    /**
     * 发送粘性事件消息
     * @param event
     */
    fun <T> postSticky(event: EventMessage<T>) {
        EventBus.getDefault().postSticky(event)
    }

    /**
     * 移除粘性事件消息
     * @param event
     */
    fun <T> removeSticky(event: EventMessage<T>) {
        EventBus.getDefault().removeStickyEvent(event)
    }

    /**
     * 移除所有粘性事件消息
     * @param event
     */
    fun <T> removeAllSticky() {
        EventBus.getDefault().removeAllStickyEvents()
    }
}