package com.zhumj.androidkit.base

import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * @author Created by zhumj
 * @date 2022/4/23
 * @description MVP 中的 Presenter 基类
 */
open class BasePresenter<out V>(view: V) {

    private var mViewRef: Reference<V>? = null

    init {
        attachView(view)
    }

    /**
     * 关联View
     */
    private fun attachView(view: V) {
        mViewRef = WeakReference(view)
    }

    /**
     * 销毁View
     */
    private fun detachView() {
        if (isViewAttached) {
            mViewRef?.clear()
            mViewRef = null
        }
    }

    /**
     * 是否已经关联
     */
    private val isViewAttached: Boolean
        get() = mViewRef != null && view != null

    /**
     * 获取
     */
    val view: V?
        get() = mViewRef?.get()

    open fun onDestroy() {
        detachView()
    }
}