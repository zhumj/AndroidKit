package com.zhumj.androidkit.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.*
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

/**
 * @author Created by zhumj
 * @date 2022/4/27 11:29
 * @description : 位置相关
 */
class LocationUtil(private val context: Context) {

    private val locationManager: LocationManager =
        context.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    data class LocaleOptions(
        val providerTimeout: Long = 2000L,
        val preferCriteria: Criteria = defaultCriteria(),
        val preferUpdateTime: Long = 1000L,
        val preferUpdateDistance: Float = 1f
    )

    interface OnGetLocationListener{
        fun isLocationDisable() {

        }
        fun onLocationCallBack(location: Location?) {

        }
        fun onAddressCallBack(address: Address?) {

        }
    }

    /**
     * 判断位置信息开关是否打开
     */
    fun isLocationEnabled(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            locationManager.isLocationEnabled
        } else {
            try {
                Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE) != Settings.Secure.LOCATION_MODE_OFF
            } catch (e: SettingNotFoundException) {
                e.printStackTrace()
                false
            }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun getAddress(lifecycleScope: LifecycleCoroutineScope, listener: OnGetLocationListener? = null, option: LocaleOptions = LocaleOptions()) {
        lifecycleScope.launch {
            if (isLocationEnabled()) {
                locale(option).also {
                    listener?.onLocationCallBack(it)
                    listener?.onAddressCallBack(address(it))
                }
            } else {
                listener?.isLocationDisable()
            }
        }
    }

    /**
     * 获取地址信息
     */
    fun address(location: Location?): Address? {
        if (location == null) return null
        return try {
            Geocoder(context).getFromLocation(
                location.latitude, location.longitude, 1
            ).firstNotNullOfOrNull {
                it
            }
        } catch (e: RuntimeException) {
            e.printStackTrace()
            null
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    fun getLocation(lifecycleScope: LifecycleCoroutineScope, listener: OnGetLocationListener? = null, option: LocaleOptions = LocaleOptions()) {
        lifecycleScope.launch {
            if (isLocationEnabled()) {
                listener?.onLocationCallBack(locale(option))
            } else {
                listener?.isLocationDisable()
            }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    suspend fun locale(option: LocaleOptions = LocaleOptions()): Location? =
        locationManager.getPreferProviders(option.preferCriteria)
            .firstNotNullOfOrNull {
                withTimeoutOrNull(option.providerTimeout) {
                    localeByProvider(it, option.preferUpdateTime, option.preferUpdateDistance)
                } ?: locationManager.getLastKnownLocation(it)
            }

    private var locationUpdater: LocationListener? = null

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    private suspend fun localeByProvider(
        provider: String,
        minTime: Long = 1000L,
        minDistance: Float = 1f
    ): Location? {
        locationUpdater?.let { locationManager.removeUpdates(it) }
        return suspendCancellableCoroutine { c ->
            locationUpdater = object : LocationListener {
                override fun onLocationChanged(p0: Location) {
                    if (c.isActive) {
                        c.resume(p0)
                    }
                }

                override fun onProviderDisabled(provider: String) {
                    if (c.isActive) {
                        c.resume(null)
                    }
                }

                override fun onProviderEnabled(provider: String) {

                }
            }.also {
                locationManager.requestLocationUpdates(
                    provider, minTime, minDistance,
                    it
                )
                c.invokeOnCancellation { _ ->
                    locationManager.removeUpdates(it)
                }
            }
        }
    }

    companion object {
        /**
         * 扩展 LocationManager，获取最优 Provider
         */
        fun LocationManager.getPreferProviders(criteria: Criteria = defaultCriteria()): List<String> {
            val bestProvider = this.getBestProvider(criteria, true)
            val enableProviders = this.getProviders(true).toMutableList()
            if (bestProvider != null) {
                //将首选提供商移至第一
                enableProviders.remove(bestProvider)
                enableProviders.add(0, bestProvider)
            }
            return enableProviders
        }

        fun defaultCriteria() = Criteria().apply {
            accuracy = Criteria.ACCURACY_COARSE//低精度
            isAltitudeRequired = false//不要求海拔
            isBearingRequired = false//不要求方位
            isCostAllowed = false//不允许有花费
            powerRequirement = Criteria.POWER_LOW//低功耗
        }

        /**
         * 进入“位置信息”设置界面
         */
        fun gotoLocationSettings(context: Context) {
            //触发点击事件
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

}