package com.zhumj.androidkitproject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.zhumj.androidkit.base.BaseActivity
import com.zhumj.androidkit.builder.ToastBuilder
import com.zhumj.androidkit.builder.ToastType
import com.zhumj.androidkit.premulticlick.OnPreMultiClickListener
import com.zhumj.androidkitproject.databinding.ActivityMainBinding
import com.zhumj.androidkitproject.mvp.contract.MainContract
import com.zhumj.androidkitproject.mvp.presenter.MainPresenter

class MainActivity : BaseActivity<ActivityMainBinding, MainPresenter>(), MainContract.View {

    private val TAG = "MainActivity"

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun obtainPresenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 添加 防止短时间内多次点击 实现，默认500毫秒，onPreMultiClickListener 取1000毫秒
        binding.btn1.setOnClickListener(onPreMultiClickListener)
        binding.btn2.setOnClickListener(onPreMultiClickListener)
        binding.btn3.setOnClickListener(onPreMultiClickListener)
    }

    override fun onResume() {
        super.onResume()
        // MVP 获取数据
        presenter?.queryDates()
    }

    private val onPreMultiClickListener = object : OnPreMultiClickListener(1000) {
        override fun onValidClick(view: View) {
            if (view is  Button) {
                Log.d(TAG, view.text.toString() + "点击有效")
            }
        }

        override fun onInvalidClick(view: View) {
            if (view is  Button) {
                Log.d(TAG, view.text.toString() + "点击无效")
            }
        }
    }

    /**
     * MVP View 获取数据成功回调
     */
    override fun queryDatesSuccess(dates: List<Int>) {
        ToastBuilder(this)
            .setMessage("数据获取成功")
            .setToastType(ToastType.SUCCESS)
            .create()
            .show()
    }

    /**
     * MVP View 获取数据失败回调
     */
    override fun queryDatesFailure(errCode: Int, errMsg: String) {
        ToastBuilder(this)
            .setMessage("数据获取失败")
            .setToastType(ToastType.ERROR)
            .create()
            .show()
    }

}