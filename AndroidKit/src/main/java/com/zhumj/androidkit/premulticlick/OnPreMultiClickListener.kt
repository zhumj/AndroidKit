package com.zhumj.androidkit.premulticlick

import android.view.View

/**
 * @author Created by zhumj
 * @date 2022/4/26 11:40
 * @description : 防止短时间内多次点击，默认 500 毫秒内重复点击无效
 */
abstract class OnPreMultiClickListener(private val intervalTime: Long = 500): View.OnClickListener {

    //用来缓存最近点击过的
    private val linkedHashMap: LinkedHashMap<String, Long> =
        object : LinkedHashMap<String, Long>() {
            override fun removeEldestEntry(eldest: Map.Entry<String, Long>): Boolean {
                return size > 10
            }
        }

    override fun onClick(p0: View) {
        val key = p0.hashCode().toString()

        val currentTime = System.currentTimeMillis()
        var lastTime: Long = 0
        if (linkedHashMap.containsKey(key)) {
            val time: Long? = linkedHashMap[key]
            lastTime = time ?: 0
        }
        val diffTime = currentTime - lastTime
        if (diffTime > intervalTime) {
            linkedHashMap[key] = currentTime
            onValidClick(p0)
        } else {
            onInvalidClick(p0)
        }
    }

    /**
     * 有效点击回调
     */
    abstract fun onValidClick(view: View)

    /**
     * 无效点击回调
     */
    open fun onInvalidClick(view: View) { }
}