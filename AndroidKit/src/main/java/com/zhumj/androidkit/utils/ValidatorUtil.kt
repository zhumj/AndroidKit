package com.zhumj.androidkit.utils

import java.util.regex.Pattern

/**
 * @author Created by zhumj
 * @date 2022/4/23 16:22
 * @description : 正则验证工具类
 */
object ValidatorUtil {

    /**
     * 校验用户名：a-z、A-Z、0-9、"_",不包含中文和特殊字符，
     * 必须以字母开头，不能以“_”结尾，
     * 默认6-18位
     */
    fun isUserName(username: String, min: Int = 5, max: Int = 17): Boolean {
        return Pattern.matches("^[a-zA-Z]\\w{$min,$max}(?<!_)$", username)
    }

    /**
     * 校验简单的密码：不包含特殊字符, 至少需要一个 字母+数字，
     * 默认8-20位
     */
    fun isSimplePassword(password: String, min: Int = 8, max: Int = 20): Boolean {
        return Pattern.matches("^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{$min,$max}\$", password)
    }

    /**
     * 校验普通的密码：至少需要一个 字母+数字+特殊字符，
     * 默认8-20位
     */
    fun isOrdinaryPassword(password: String, min: Int = 8, max: Int = 20): Boolean {
        return Pattern.matches("^(?![0-9a-zA-Z]+\$)(?![a-zA-Z_@#\$&*]+\$)[0-9a-zA-Z_@#\$&*]{$min,$max}\$", password)
    }

    /**
     * 校验复杂的密码：至少需要一个 小写字母+大写字母+数字+特殊字符，
     * 默认8-20位
     */
    fun isComplexPassword(password: String, min: Int = 8, max: Int = 20): Boolean {
        return Pattern.matches("^(?![0-9a-zA-Z]+\$)(?![0-9a-z_@#\$&*]+\$)(?![0-9A-Z_@#\$&*]+\$)(?![a-zA-Z_@#\$&*]+\$)(?![0-9_@#\$&*]+\$)(?![a-z_@#\$&*]+\$)(?![A-Z_@#\$&*]+\$)[0-9a-zA-Z_@#\$&*]{$min,$max}\$", password)
    }

    /**
     * 校验手机号
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    fun isMobile(mobile: String): Boolean {
        return Pattern.matches("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$", mobile)
    }

    /**
     * 校验邮箱
     */
    fun isEmail(email: String): Boolean {
        return Pattern.matches("^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", email)
    }

    /**
     * 校验汉字
     */
    fun isChinese(chinese: String): Boolean {
        return Pattern.matches("^[\u4e00-\u9fa5]$", chinese)
    }

    /**
     * 校验数字，包含正负整数、浮点数
     */
    fun isNumber(number: String): Boolean {
        return Pattern.matches("^[+-]?(([0-9]([0-9]*|[.][0-9]+))|([.][0-9]+))$", number)
    }

    /**
     * 校验数字，包含正负整数
     */
    fun isInt(int: String): Boolean {
        return Pattern.matches("^[+-]?[0-9]\\d*$", int)
    }

    /**
     * 校验数字，包含正负浮点数
     */
    fun isDouble(double: String): Boolean {
        return Pattern.matches("^[+-]?[0-9]*[.][0-9]+$", double)
    }

}