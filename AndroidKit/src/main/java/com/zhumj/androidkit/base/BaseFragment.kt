package com.zhumj.androidkit.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zhumj.androidkit.eventbus.EventBusUtil
import com.zhumj.androidkit.eventbus.EventMessage
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author Created by zhumj
 * @date 2022/4/23
 * @description Fragment 基类
 */
abstract class BaseFragment<VB : ViewBinding, BP: BasePresenter<*>?>: Fragment() {

    private lateinit var _binding: VB

    protected val mViewBinding get() = _binding
    protected var mPresenter: BP? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        if (mPresenter == null) {
            mPresenter = obtainPresenter()
            if (mPresenter != null) {
                lifecycle.addObserver(mPresenter!!)
            }
        }

        if (isRegisteredEventBus()) {
            EventBusUtil.register(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getViewBinding(inflater, container)
        return _binding.root
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    protected abstract fun obtainPresenter(): BP?

    /**
     * 是否注册事件分发
     * @return true 注册；false 不注册，默认不注册
     */
    open fun isRegisteredEventBus(): Boolean = false

    /**
     * 接收到分发的事件
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onReceiveEvent(event: EventMessage<*>) {
    }

    /**
     * 接受到分发的粘性事件
     * @param event 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    open fun onReceiveStickyEvent(event: EventMessage<*>) {
    }

    /**
     * Description: 初始化工具栏
     * @param  isShowBack   是否显示返回键
     * @param  isShowTitle  是否显示标题
     */
    protected fun initToolBar(isShowBack: Boolean = false, isShowTitle: Boolean = false) {
        initToolBar(null, isShowBack, isShowTitle)
    }
    protected fun initToolBar(toolbar: Toolbar?, isShowBack: Boolean = false, isShowTitle: Boolean = false) {
        setHasOptionsMenu(true)

        val mActivity: AppCompatActivity = activity as AppCompatActivity
        if (toolbar != null) {
            mActivity.setSupportActionBar(toolbar)
        }
        mActivity.supportActionBar?.setDisplayShowTitleEnabled(isShowTitle)
        mActivity.supportActionBar?.setDisplayShowHomeEnabled(isShowBack)
        mActivity.supportActionBar?.setDisplayHomeAsUpEnabled(isShowBack)
    }

    override fun onDestroyView() {
        if (isRegisteredEventBus()) {
            EventBusUtil.unregister(this)
        }
        super.onDestroyView()
    }

}