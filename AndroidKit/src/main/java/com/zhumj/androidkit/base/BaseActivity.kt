package com.zhumj.androidkit.base

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.vmadalin.easypermissions.EasyPermissions
import com.zhumj.androidkit.eventbus.EventBusUtil
import com.zhumj.androidkit.eventbus.EventMessage
import com.zhumj.androidkit.utils.SoftKeyboardUtil
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author Created by zhumj
 * @date 2022/4/23
 * @description AppCompatActivity 基类
 */
abstract class BaseActivity<VB : ViewBinding, BP: BasePresenter<*>?>: AppCompatActivity() {

    private lateinit var _binding: VB

    protected val mViewBinding get() = _binding
    protected var mPresenter: BP? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(_binding.root)

        init()
    }

    /**
     * 初始化部分工具类
     */
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

    /**
     * Description: 初始化工具栏
     * @param  isShowBack   是否显示返回键
     * @param  isShowTitle  是否显示标题
     */
    protected fun initToolBar(toolbar: Toolbar? = null, isShowBack: Boolean = false, isShowTitle: Boolean = false) {
        if (toolbar != null) {
            setSupportActionBar(toolbar)
        }
        supportActionBar?.setDisplayShowTitleEnabled(isShowTitle)
        supportActionBar?.setDisplayShowHomeEnabled(isShowBack)
        supportActionBar?.setDisplayHomeAsUpEnabled(isShowBack)
    }

    protected abstract fun getViewBinding(): VB
    protected abstract fun obtainPresenter(): BP?

    /**
     * 是否注册事件分发
     * @return true 注册；false 不注册，默认不注册
     */
    open fun isRegisteredEventBus(): Boolean = false
    /**
     * 是否启用点击空白区域隐藏软键盘
     * @return true 启用；false 不启用
     */
    open fun enableHideSoftKeyboardByClickBlank(): Boolean = false

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

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (enableHideSoftKeyboardByClickBlank()) {
            // 点击空白隐藏键盘
            SoftKeyboardUtil.touchToHideSoft(this, ev, currentFocus)
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * EasyPermissions 相关
     */
    // 禁止EasyPermissions框架弹出Dialog
    override fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        return false
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onDestroy() {
        if (isRegisteredEventBus()) {
            EventBusUtil.unregister(this)
        }
        super.onDestroy()
    }

}