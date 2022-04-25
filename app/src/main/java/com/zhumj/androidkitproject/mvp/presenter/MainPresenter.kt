package com.zhumj.androidkitproject.mvp.presenter

import com.zhumj.androidkit.base.BasePresenter
import com.zhumj.androidkitproject.mvp.contract.MainContract
import com.zhumj.androidkitproject.mvp.model.MainModel

class MainPresenter(view: MainContract.View): BasePresenter<MainContract.View>(view), MainContract.Presenter {

    private val model = MainModel()
    var dates: List<String> = ArrayList()

    override fun queryDates() {
        dates = model.queryDatesDates()
//        view?.queryDatesSuccess(dates)
//        view?.queryDatesFailure(-1, "数据获取失败")
    }

}