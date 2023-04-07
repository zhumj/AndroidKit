package com.zhumj.androidkit.utils

import android.content.Context
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @Author Created by zhumj
 * @Date 2023/4/7 15:03
 * @Description 点击空白处自动隐藏软键盘工具类
 */
object SoftKeyboardUtil {
    /**
     * 点击空白隐藏键盘
     * @param context
     * @param event 点击事件
     * @param view 当前拥有焦点的View
     */
    fun touchToHideSoft(context: Context, ev: MotionEvent, view: View?) {
        if (view != null && ev.action == MotionEvent.ACTION_DOWN) {
            if (isHideInput(view, ev)) {
                hideSoftInput(context, view.windowToken)
            }
        }
    }

    // 判定是否需要隐藏
    private fun isHideInput(view: View, ev: MotionEvent): Boolean {
        if (view is EditText) {
            val l = intArrayOf(0, 0)
            view.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + view.getHeight()
            val right = left + view.getWidth()
            return ev.x <= left || ev.x >= right || ev.y <= top || ev.y >= bottom
        }
        return false
    }

    // 隐藏软键盘
    private fun hideSoftInput(context: Context, token: IBinder?) {
        if (token != null) {
            val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}