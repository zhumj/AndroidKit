package com.zhumj.androidkitproject

import android.Manifest
import android.annotation.SuppressLint
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.annotations.AfterPermissionGranted
import com.zhumj.androidkit.base.BaseActivity
import com.zhumj.androidkit.builder.SnackBarBuilder
import com.zhumj.androidkit.builder.ToastBuilder
import com.zhumj.androidkit.premulticlick.OnPreMultiClickListener
import com.zhumj.androidkit.utils.LocationUtil
import com.zhumj.androidkitproject.databinding.ActivityMainBinding
import com.zhumj.androidkitproject.mvp.contract.MainContract
import com.zhumj.androidkitproject.mvp.presenter.MainPresenter

class MainActivity : BaseActivity<ActivityMainBinding, MainPresenter>(), MainContract.View {

    private val TAG = "111111111111111"

    private lateinit var locationUtil: LocationUtil

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

        locationUtil = LocationUtil(this)
    }

    override fun onResume() {
        super.onResume()
        // MVP 获取数据
        presenter?.queryDates()
    }

    private val onPreMultiClickListener = object : OnPreMultiClickListener(2000) {
        override fun onValidClick(view: View) {
            if (view is  Button) {
                SnackBarBuilder.getInstance(this@MainActivity)
                    .setToastType(SnackBarBuilder.ToastType.SUCCESS)
                    .setMessage(view.text.toString() + "点击有效")
                    .showToast(view)
            }
            checkLocationPermission()
        }

        override fun onInvalidClick(view: View) {
            if (view is  Button) {
                SnackBarBuilder.getInstance(this@MainActivity)
                    .setToastType(SnackBarBuilder.ToastType.ERROR)
                    .setDuration(Snackbar.LENGTH_INDEFINITE)
                    .setMessage(view.text.toString() + "点击无效")
                    .showToast(view, SnackBarBuilder.ActionConfig {
                        SnackBarBuilder.getInstance(this@MainActivity).dismissSnackBar()
                    })
            }
        }
    }

    /**
     * 检查是否有位置权限
     */
    private fun checkLocationPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            getLocation()
        } else {
            EasyPermissions.requestPermissions(this, "", 10086,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        }
    }

    /**
     * 获取位置并且检查手机号码是否合法
     */
    @AfterPermissionGranted(10086)
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationUtil.getAddress(lifecycleScope, object : LocationUtil.OnGetLocationListener {
            // 位置信息开关处于禁用状态
            override fun isLocationDisable() {
                // 弹出Dialog
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("“位置信息”未开启")
                    .setMessage("请前往设置开启定位")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("设置") { _, _ -> LocationUtil.gotoLocationSettings(this@MainActivity) }
                    .create()
                    .show()
            }

            override fun onAddressCallBack(address: Address?) {
                validPhone(address)
            }
        })

        /*
        locationUtil.getLocation(lifecycleScope, object : LocationUtil.OnGetLocationListener {
            override fun isLocationDisable() {

            }

            override fun onLocationCallBack(location: Location?) {

            }
        })
        */

        /*
        lifecycleScope.launch {
            locationUtil.isLocationEnabled().also {
                if (it) {
                    locationUtil.locale().also { location: Location? ->
                        locationUtil.address(location).also { address: Address? ->
                            validPhone(address)
                        }
                    }
                }
            }
        }
        */
    }

    /**
     * 验证手机号合法性
     */
    private fun validPhone(address: Address?) {
        Log.d(TAG, "${address?.countryName ?: "null"}, ${address?.countryCode ?: "null"}")
        val countryCode = address?.countryCode
        if (!countryCode.isNullOrBlank()) {
            try {
                val phoneNumberUtil = PhoneNumberUtil.getInstance()
                val phoneNumber = phoneNumberUtil.parse("15800235429", countryCode)
                Log.d(TAG, "${phoneNumber.countryCode}, ${phoneNumber.nationalNumber}")
                Log.d(TAG, "手机号是否合法：${phoneNumberUtil.isValidNumber(phoneNumber)}")
            } catch (e: NumberParseException) {
                Log.d(TAG, "号码解析异常: $e")
            }
        }
    }

    /**
     * MVP View 获取数据成功回调
     */
    override fun queryDatesSuccess(dates: List<Int>) {
        ToastBuilder(this)
            .setMessage("数据获取成功")
            .setToastType(ToastBuilder.ToastType.SUCCESS)
            .create()
            .show()
    }

    /**
     * MVP View 获取数据失败回调
     */
    override fun queryDatesFailure(errCode: Int, errMsg: String) {
        ToastBuilder(this)
            .setMessage("数据获取失败")
            .setToastType(ToastBuilder.ToastType.ERROR)
            .create()
            .show()
    }

}