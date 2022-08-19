package com.zhumj.androidkit.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

/**
 * @Author Created by zhumj
 * @Date 2022/8/19 11:37
 * @Description Gson解析工具
 */
object GsonUtil {
    /**
     * 把数据转为Json字符串
     */
    fun parse(json: Any): String {
        return Gson().toJson(json)
    }

    /**
     * 把Json字符串转为特定数据
     * 这里需要使用内联函数 inline，不然会报错：
     * java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to
     */
    inline fun <reified T> from(jsonStr: String): T? {
        return try {
            val type = object : TypeToken<T>() {}.type
            Gson().fromJson(jsonStr, type)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }
}