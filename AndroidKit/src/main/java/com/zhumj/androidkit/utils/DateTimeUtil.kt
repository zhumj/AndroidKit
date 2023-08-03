package com.zhumj.androidkit.utils

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.annotation.StringDef
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Created by zhumj
 * @date 2022/4/23 15:07
 * @description : 日期/时间 工具类
 */
object DateTimeUtil {

    /**
     * 指定日期格式 yyyy-MM-dd HH:mm:ss
     */
    const val DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss"

    /**
     * 指定日期格式 yyyy-MM-dd
     */
    const val DATE_FORMAT_2 = "yyyy-MM-dd"

    /**
     * 指定日期格式 yyyy-MM-dd HH:mm
     */
    const val DATE_FORMAT_3 = "yyyy-MM-dd HH:mm"

    @StringDef(DATE_FORMAT_1, DATE_FORMAT_2, DATE_FORMAT_3)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class FORMAT

    /**
     * 获取当前日期与时间
     * @return 12小时制 en: April 19, 2022, 1:30 PM   zh: 2022年4月19日 下午1:30
     *         24小时制 en: April 19, 2022, 13:30     zh: 2022年4月19日 13:30
     */
    fun getNowLocalDateTime(context: Context?): String? {
        return getLocalDateTime(context, Calendar.getInstance())
    }
    fun getLocalDateTime(context: Context?, calendar: Calendar): String? {
        return getLocalDateTime(context, calendar.timeInMillis)
    }
    fun getLocalDateTime(context: Context?, timeInMillis: Long): String? {
        return DateUtils.formatDateTime(
            context,
            timeInMillis,
            DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
        )
    }

    /**
     * 获取当前日期
     * @return en: April 19, 2022   zh: 2022年4月19日
     */
    fun getNowLocalDate(context: Context?): String? {
        return getLocalDate(context, Calendar.getInstance())
    }
    fun getLocalDate(context: Context?, calendar: Calendar): String? {
        return getLocalDate(context, calendar.timeInMillis)
    }
    fun getLocalDate(context: Context?, timeInMillis: Long): String? {
        return DateUtils.formatDateTime(
            context,
            timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
        )
    }

    /**
     * 获取当前时间
     * @return 12小时制 en: 1:30 PM   zh: 下午 1:30
     *         24小时制 en: 13:30     zh: 13:30
     */
    fun getNowLocalTime(context: Context?): String? {
        return getLocalTime(context, Calendar.getInstance())
    }
    fun getLocalTime(context: Context?, calendar: Calendar): String? {
        return getLocalTime(context, calendar.timeInMillis)
    }
    fun getLocalTime(context: Context?, timeInMillis: Long): String? {
        return DateUtils.formatDateTime(context, timeInMillis, DateUtils.FORMAT_SHOW_TIME)
    }

    /**
     * 获取默认时区
     * @return TimeZone
     */
    fun getDefaultTimeZone(): TimeZone {
        return TimeZone.getDefault()
    }

    /**
     * 获取默认时区名称
     * @return 如：GMT+08:00 中国标准时间
     */
    fun getDefaultTimeZoneName(): String {
        return getTimeZoneName(getDefaultTimeZone())
    }

    /**
     * 获取时区名称
     * @return 如：GMT+08:00 中国标准时间
     */
    fun getTimeZoneName(zone: TimeZone): String {
        return getTimeZoneShortName(zone) + " " + getTimeZoneLongName(zone)
    }

    /**
     * 获取时区短名称
     * @return 如：GMT+08:00
     */
    fun getTimeZoneShortName(zone: TimeZone): String {
        return zone.getDisplayName(true, TimeZone.SHORT)
    }

    /**
     * 获取时区长名称
     * @return 如：中国标准时间
     */
    fun getTimeZoneLongName(zone: TimeZone): String {
        return zone.getDisplayName(false, TimeZone.LONG)
    }

    /**
     * 判断是上午还是下午，一般12小时制使用
     */
    fun isAM(): Boolean {
        return Calendar.getInstance()[Calendar.AM_PM] == Calendar.AM
    }

    /**
     * 判断系统使用的是24小时制还是12小时制
     */
    fun is24HourFormat(context: Context?): Boolean {
        return DateFormat.is24HourFormat(context)
    }

    fun getSimpleFormat(format: String): SimpleDateFormat = SimpleDateFormat(format, Locale.getDefault())

    /**
     * 根据指定格式，获取现在时间
     */
    fun getNowDateFormat(format: String): String {
        val currentTime = Date()
        return getSimpleFormat(format).format(currentTime)
    }

    /**
     * 把String日期转成指定格式
     */
    fun getDateFormat(dateString: String, format: String): String {
        return try {
            val simpleDateFormat = getSimpleFormat(format)
            val date: Date? = simpleDateFormat.parse(dateString)
            simpleDateFormat.format(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
            dateString
        }
    }

    /**
     * 根据生日计算年龄
     */
    fun getAgeByBirthday(birthday: String): Int {
        try {
            val calendar = Calendar.getInstance()
            val yearNow = calendar.get(Calendar.YEAR)
            val monthNow = calendar.get(Calendar.MONTH)
            val dayOfMonthNow = calendar.get(Calendar.DAY_OF_MONTH)

            calendar.time = getSimpleFormat(DATE_FORMAT_2).parse(birthday)!!
            val yearBirth = calendar.get(Calendar.YEAR)
            val monthBirth = calendar.get(Calendar.MONTH)
            val dayOfMonthBirth = calendar.get(Calendar.DAY_OF_MONTH)

            var age = yearNow - yearBirth
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if(dayOfMonthNow < dayOfMonthBirth) age--
                } else {
                    age--
                }
            }
            return age
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

}