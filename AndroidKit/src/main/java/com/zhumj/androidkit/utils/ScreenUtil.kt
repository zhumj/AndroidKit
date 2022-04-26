package com.zhumj.androidkit.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * @author Created by zhumj
 * @date 2022/4/23 15:48
 * @description : 屏幕相关
 */
object ScreenUtil {
    /**
     * 获得屏幕宽度
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
//        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val outMetrics = DisplayMetrics()
//        wm.defaultDisplay.getMetrics(outMetrics)
//        return outMetrics.widthPixels
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 获得屏幕高度
     * @param context
     * @return
     */
    fun getScreenHeight(context: Context): Int {
//        val wm = context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val outMetrics = DisplayMetrics()
//        wm.defaultDisplay.getMetrics(outMetrics)
//        return outMetrics.heightPixels
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 获得状态栏的高度
     * @param context
     */
    @SuppressLint("PrivateApi")
    fun getStatusHeight(context: Context): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(
                clazz.getField("status_bar_height")
                    .get(`object`)?.toString() ?: "0"
            )
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusHeight
    }

}