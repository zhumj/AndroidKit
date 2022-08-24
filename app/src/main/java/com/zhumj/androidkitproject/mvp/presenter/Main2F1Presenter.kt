package com.zhumj.androidkitproject.mvp.presenter

import android.util.Log
import com.zhumj.androidkit.base.BasePresenter
import com.zhumj.androidkitproject.mvp.contract.Main2F1Contract
import androidx.lifecycle.LifecycleOwner

/**
 * 这里是 MVP 中对逻辑进行处理的地方，通过 View 回调告诉 Activity 如何更新 UI
 */
class Main2F1Presenter(view: Main2F1Contract.View): BasePresenter<Main2F1Contract.View>(view), Main2F1Contract.Presenter {

    var tag = "Main2 Fragment1"

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d("1111111111111111111", "${tag}: onDestroy(owner: LifecycleOwner)")
        super.onDestroy(owner)
    }

}