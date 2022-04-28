package com.zhumj.androidkit.singleton

/**
 * @author Created by zhumj
 * @date 2022/4/28 13:32
 * @description : Kotlin 单例辅助类，T：需要实现单例的类，A：传递的参数，如 Context
 */
open class SingletonHolder<out T: Any, in A>(private val creator: (A) -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        return if (instance != null) {
            instance!!
        } else {
            synchronized(this) {
                instance = creator(arg)
                instance!!
            }
        }
    }
}