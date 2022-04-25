package com.zhumj.androidkitproject

import com.zhumj.androidkit.base.BaseActivity
import com.zhumj.androidkit.builder.ToastBuilder
import com.zhumj.androidkit.builder.ToastType
import com.zhumj.androidkitproject.databinding.ActivityMainBinding
import com.zhumj.androidkitproject.mvp.contract.MainContract
import com.zhumj.androidkitproject.mvp.presenter.MainPresenter

class MainActivity : BaseActivity<ActivityMainBinding, MainPresenter>(), MainContract.View {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun obtainPresenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        // MVP 获取数据
        presenter?.queryDates()
    }

    override fun queryDatesSuccess(dates: List<Int>) {
        ToastBuilder(this)
            .setMessage("数据获取成功")
            .setToastType(ToastType.SUCCESS)
            .create()
            .show()
    }

    override fun queryDatesFailure(errCode: Int, errMsg: String) {
        ToastBuilder(this)
            .setMessage("数据获取失败")
            .setToastType(ToastType.ERROR)
            .create()
            .show()
    }

}