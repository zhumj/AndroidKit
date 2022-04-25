package com.zhumj.androidkit.utils

import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * @author Created by zhumj
 * @date 2022/4/23 16:17
 * @description : SharedPreferences工具类
 */
object SPUtil {

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param data
     */
    fun put(context: Context, key: String, data: Any) {
        val sp: SharedPreferences = context.getSharedPreferences("${AppUtil.getAppName(context)}_sp_data", Context.MODE_PRIVATE)
        val editor = sp.edit()
        when (data) {
            is String -> {
                editor.putString(key, data)
            }
            is Int -> {
                editor.putInt(key, data)
            }
            is Boolean -> {
                editor.putBoolean(key, data)
            }
            is Float -> {
                editor.putFloat(key, data)
            }
            is Long -> {
                editor.putLong(key, data)
            }
            else -> {
                editor.putString(key, data.toString())
            }
        }
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    operator fun get(context: Context, key: String, defaultObject: Any): Any? {
        val sp: SharedPreferences = context.getSharedPreferences("${AppUtil.getAppName(context)}_sp_data", Context.MODE_PRIVATE)
        when (defaultObject) {
            is String -> {
                return sp.getString(key, defaultObject)
            }
            is Int -> {
                return sp.getInt(key, defaultObject)
            }
            is Boolean -> {
                return sp.getBoolean(key, defaultObject)
            }
            is Float -> {
                return sp.getFloat(key, defaultObject)
            }
            is Long -> {
                return sp.getLong(key, defaultObject)
            }
            else -> return null
        }
    }

    /**
     * 移除某个key值已经对应的值
     * @param context
     * @param key
     */
    fun remove(context: Context, key: String) {
        val sp = context.getSharedPreferences("${AppUtil.getAppName(context)}_sp_data", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 清除所有数据
     * @param context
     */
    fun clear(context: Context) {
        val sp = context.getSharedPreferences("${AppUtil.getAppName(context)}_sp_data", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }

    /**
     * 查询某个key是否已经存在
     * @param context
     * @param key
     * @return
     */
    fun contains(context: Context, key: String): Boolean {
        val sp = context.getSharedPreferences("${AppUtil.getAppName(context)}_sp_data", Context.MODE_PRIVATE)
        return sp.contains(key)
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    fun getAll(context: Context): Map<String?, *>? {
        val sp = context.getSharedPreferences("${AppUtil.getAppName(context)}_sp_data", Context.MODE_PRIVATE)
        return sp.all
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private object SharedPreferencesCompat {

        private val sApplyMethod: Method? = findApplyMethod()

        /**
         * 反射查找apply的方法
         * @return
         */
        private fun findApplyMethod(): Method? {
            try {
                val clz: Class<*> = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) { }
            return null
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalArgumentException) {
            } catch (e: IllegalAccessException) {
            } catch (e: InvocationTargetException) {
            }
            editor.commit()
        }
    }
}