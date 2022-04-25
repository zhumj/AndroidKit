package com.zhumj.androidkit.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

/**
 * @author Created by zhumj
 * @date 2022/4/23
 * @description App 相关工具类
 */
object AppUtil {
    /**
     * 获取应用程序名称
     * @return 应用程序名称
     */
    fun getAppName(context: Context): String? {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取应用程序版本名称信息
     * @return 当前应用的版本名称
     */
    fun getVersionName(context: Context): String? {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取应用程序的版本Code信息
     * @return 版本code
     */
    fun getVersionCode(context: Context): Int {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode.toInt()
            } else {
                packageInfo.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 获取APK安装路径
     */
    fun getSelfApkPath(context: Context): String? {
        try {
            val applicationInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA or PackageManager.GET_SHARED_LIBRARY_FILES
            )
            return applicationInfo.sourceDir
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 检查APK是否安装
     * @param pkgName 包名
     */
    fun isPkgInstalled(context: Context, pkgName: String): Boolean {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = context.packageManager.getPackageInfo(pkgName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return packageInfo != null
    }

}