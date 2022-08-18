package com.zhumj.androidkitproject.mvp.presenter

import com.zhumj.androidkit.base.BasePresenter
import com.zhumj.androidkitproject.mvp.contract.MainContract
import com.zhumj.androidkitproject.mvp.model.MainModel
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch

/**
 * 这里是 MVP 中对逻辑进行处理的地方，通过 View 回调告诉 Activity 如何更新 UI
 */
class MainPresenter(view: MainContract.View): BasePresenter<MainContract.View>(view), MainContract.Presenter {

    private val model = MainModel()
    var dates: List<Int> = ArrayList()

    override fun queryDates(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            dates = model.queryDates().also {
                if (it.isEmpty()) {
                    view?.queryDatesFailure(-1, "数据获取失败")
                } else {
                    view?.queryDatesSuccess(dates)
                }
            }
        }
    }

}