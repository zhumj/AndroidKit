package com.zhumj.androidkit.eventbus

/**
 * @author Created by zhumj
 * @date 2022/4/23
 * @description EventBus 数据实体
 */
data class EventMessage<T> (
    var code: Int = 0,
    var data: T? = null
)
