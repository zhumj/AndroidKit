package com.zhumj.androidkit.utils

import android.content.Context
import android.os.Build
import android.provider.Settings

/**
 * @author Created by zhumj
 * @date 2022/4/23 15:42
 * @description : 设备相关工具类
 */
object DeviceUtil {
    /**
     * 获取设备的唯一标识，deviceId
     */
    fun getDeviceId(context: Context): String? {
        return Settings.System.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    /**
     * 获取手机品牌
     */
    fun getPhoneBrand(): String? {
        return Build.BRAND
    }

    /**
     * 获取手机型号
     */
    fun getPhoneModel(): String? {
        return Build.MODEL
    }

    /**
     * 获取手机Android API等级（22、23 ...）
     */
    fun getBuildLevel(): Int {
        return Build.VERSION.SDK_INT
    }

    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     */
    fun getBuildVersion(): String? {
        return Build.VERSION.RELEASE
    }
}