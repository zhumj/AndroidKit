package com.zhumj.androidkit.utils

import android.graphics.Paint
import kotlin.math.abs

/**
 * @Author Created by zhumj
 * @Date 2022/6/29 15:57
 * @Description Text测量相关
 */
object TextUtil {
    /**
     * 获取字体宽度
     */
    fun getTextWidth(textPaint: Paint, text: String): Float {
        return textPaint.measureText(text)
    }

    /**
     * 获取字体高度
     */
    fun getTextHeight(textPaint: Paint): Float {
        val fontMetrics: Paint.FontMetrics = textPaint.fontMetrics
        return fontMetrics.bottom - fontMetrics.top
    }

    /**
     * 获取字体基线
     */
    fun getTextBaseLine(textPaint: Paint): Float {
        val fontMetrics: Paint.FontMetrics = textPaint.fontMetrics
        return abs(fontMetrics.top)
    }
}