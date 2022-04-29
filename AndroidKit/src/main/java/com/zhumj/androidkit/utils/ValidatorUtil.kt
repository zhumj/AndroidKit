package com.zhumj.androidkit.utils

import java.util.regex.Pattern

/**
 * @author Created by zhumj
 * @date 2022/4/23 16:22
 * @description : 正则验证工具类
 */
object ValidatorUtil {
    /**
     * 正则表达式:验证用户名(a-z,A-Z,0-9,"_",不包含中文和特殊字符，不能以“_”结尾，5-17位)
     * 如果用户名使用手机号码或邮箱 则结合手机号验证和邮箱验证
     */
    private val REGEX_USERNAME: String = "^[a-zA-Z]\\w{5,17}(?<!_)$"

    /**
     * 正则表达式:验证密码
     * 至少需要一个 小写字母+大写字母+数字+特殊字符，6-20位：
     *     ^(?![0-9a-zA-Z]+\$)(?![0-9a-z_@#$&*]+\$)(?![0-9A-Z_@#$&*]+\$)(?![a-zA-Z_@#$&*]+\$)(?![0-9_@#$&*]+\$)(?![a-z_@#$&*]+\$)(?![A-Z_@#$&*]+\$)[0-9a-zA-Z_@#$&*]{6,20}\$
     * 至少需要 一个字母+数字+特殊字符，6-20位：
     *     ^(?![0-9a-zA-Z]+$)(?![a-zA-Z_@#$&*]+$)[0-9a-zA-Z_@#$&*]{6,20}$
     * 不包含特殊字符, 至少需要一个字母+数字，6-20位：
     */
    private val REGEX_PASSWORD: String = "^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{6,20}\$"

    /**
     * 正则表达式:验证手机号
     */
    private val REGEX_MOBILE: String = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$"

    /**
     * 正则表达式:验证邮箱
     */
    private val REGEX_EMAIL: String = "^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"

    /**
     * 正则表达式:验证汉字
     */
    private val REGEX_CHINESE: String = "^[\u4e00-\u9fa5]$"


    /**
     * 校验用户名
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    fun isUserName(username: String): Boolean {
        return Pattern.matches(REGEX_USERNAME, username)
    }

    /**
     * 校验密码
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    fun isPassword(password: String): Boolean {
        return Pattern.matches(REGEX_PASSWORD, password)
    }

    /**
     * 校验手机号
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    fun isMobile(mobile: String): Boolean {
        return Pattern.matches(REGEX_MOBILE, mobile)
    }

    /**
     * 校验邮箱
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    fun isEmail(email: String): Boolean {
        return Pattern.matches(REGEX_EMAIL, email)
    }

    /**
     * 校验汉字
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    fun isChinese(chinese: String): Boolean {
        return Pattern.matches(REGEX_CHINESE, chinese)
    }

}