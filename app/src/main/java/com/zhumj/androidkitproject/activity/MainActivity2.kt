package com.zhumj.androidkitproject.activity

import android.os.Bundle
import android.view.MenuItem
import com.zhumj.androidkit.base.BaseActivity
import com.zhumj.androidkitproject.adapter.Main2ViewPagerAdapter
import com.zhumj.androidkitproject.databinding.ActivityMain2Binding
import com.zhumj.androidkitproject.mvp.contract.Main2Contract
import com.zhumj.androidkitproject.mvp.presenter.Main2Presenter

class MainActivity2 : BaseActivity<ActivityMain2Binding, Main2Presenter>(), Main2Contract.View2 {

    override fun getViewBinding(): ActivityMain2Binding {
        return ActivityMain2Binding.inflate(layoutInflater)
    }

    override fun obtainPresenter(): Main2Presenter {
        return Main2Presenter(this)
    }

    override fun enableHideSoftKeyboardByClickBlank(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolBar(isShowBack = true, isShowTitle = true)
        mViewBinding.viewPager2.adapter = Main2ViewPagerAdapter(supportFragmentManager, lifecycle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}