package com.zhumj.androidkit.utils

import java.util.regex.Pattern

/**
 * @author Created by zhumj
 * @date 2022/4/23 16:22
 * @description : 正则验证工具类
 */
object ValidatorUtil {
    /**
     * 正则表达式:验证用户名(不包含中文和特殊字符，5-17位)
     * 如果用户名使用手机号码或邮箱 则结合手机号验证和邮箱验证
     */
    private val REGEX_USERNAME: String = "^[a-zA-Z]\\w{5,17}$"

    /**
     * 正则表达式:验证密码(不包含特殊字符, 至少需要一个字母+数字，6-20位)
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
//    private val REGEX_EMAIL: String = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]+$"

    /**
     * 正则表达式:验证汉字(1-9个汉字)  {1,9} 自定义区间
     */
    private val REGEX_CHINESE: String = "^[\u4e00-\u9fa5]{1,9}$"

    /**
     * 正则表达式:验证身份证
     */
    private val REGEX_ID_CARD: String = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)"

    /**
     * 正则表达式:验证URL
     */
    private val REGEX_URL: String = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"

    /**
     * 正则表达式:验证IP地址
     */
    private val REGEX_IP_ADDR: String = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})"

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

    /**
     * 校验身份证
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    fun isIDCard(idCard: String): Boolean {
        return Pattern.matches(REGEX_ID_CARD, idCard)
    }

    /**
     * 校验URL
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    fun isUrl(url: String): Boolean {
        return Pattern.matches(REGEX_URL, url)
    }

    /**
     * 校验IP地址
     * @param ipAddress
     * @return
     */
    fun isIPAddress(ipAddress: String): Boolean {
        return Pattern.matches(REGEX_IP_ADDR, ipAddress)
    }
}