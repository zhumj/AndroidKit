package com.zhumj.androidkit.utils

import android.content.Context
import android.util.TypedValue




/**
 * @author Created by zhumj
 * @date 2022/4/26 15:08
 * @description : 分辨率相关
 */
object DensityUtil {

    /**
     * dp转px
     */
    fun dp2px(context: Context, dpValue: Float): Float {
        val scale: Float = context.resources.displayMetrics.density
        return dpValue * scale
    }

    /**
     * px转dp
     */
    fun px2dp(context: Context, pxValue: Float): Float {
        val scale: Float = context.resources.displayMetrics.density
        return pxValue / scale
    }

    /**
     * sp转px
     */
    fun sp2px(context: Context, spVal: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spVal, context.resources.displayMetrics
        )
    }

    /**
     * px转sp
     */
    fun px2sp(context: Context, pxVal: Float): Float {
        return pxVal / context.resources.displayMetrics.scaledDensity
    }
}