package com.zhumj.androidkitproject.mvp.presenter

import android.util.Log
import com.zhumj.androidkit.base.BasePresenter
import com.zhumj.androidkitproject.mvp.contract.Main2Contract
import com.zhumj.androidkitproject.mvp.model.Main2Model
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.launch

/**
 * 这里是 MVP 中对逻辑进行处理的地方，通过 View 回调告诉 Activity 如何更新 UI
 */
class Main2Presenter(view: Main2Contract.View2): BasePresenter<Main2Contract.View2>(view), Main2Contract.Presenter2 {

    private val model = Main2Model()
    var dates: List<Int> = ArrayList()

    override fun queryDates(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            dates = model.queryDates().also {

            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d("1111111111111111111", "Main2Presenter: onDestroy(owner: LifecycleOwner)")
        super.onDestroy(owner)
    }

}