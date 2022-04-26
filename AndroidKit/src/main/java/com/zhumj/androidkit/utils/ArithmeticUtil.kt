package com.zhumj.androidkit.utils

import java.math.BigDecimal

/**
 * @author Created by zhumj
 * @date 2022/4/26 15:18
 * @description : 运算相关
 */
object ArithmeticUtil {

    /**
     * 提供精确的加法运算
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    fun add(v1: String, v2: String): BigDecimal {
        var b1: BigDecimal
        var b2: BigDecimal
        try {
            b1 = BigDecimal(v1)
            b2 = BigDecimal(v2)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            b1 = BigDecimal(0)
            b2 = BigDecimal(0)
        }
        return b1.add(b2)
    }

    /**
     * 提供精确的加法运算
     * @param v1    被加数
     * @param v2    加数
     * @param scale 保留scale 位小数
     * @return 两个参数的和
     */
    fun add(v1: String, v2: String, scale: Int): BigDecimal {
        var b1: BigDecimal
        var b2: BigDecimal
        try {
            b1 = BigDecimal(v1)
            b2 = BigDecimal(v2)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            b1 = BigDecimal(0)
            b2 = BigDecimal(0)
        }
        return round(b1.add(b2), scale)
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    fun sub(v1: String, v2: String): BigDecimal {
        var b1: BigDecimal
        var b2: BigDecimal
        try {
            b1 = BigDecimal(v1)
            b2 = BigDecimal(v2)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            b1 = BigDecimal(0)
            b2 = BigDecimal(0)
        }
        return b1.subtract(b2)
    }

    /**
     * 提供精确的减法运算
     * @param v1    被减数
     * @param v2    减数
     * @param scale 保留scale 位小数
     * @return 两个参数的差
     */
    fun sub(v1: String, v2: String, scale: Int): BigDecimal {
        var b1: BigDecimal
        var b2: BigDecimal
        try {
            b1 = BigDecimal(v1)
            b2 = BigDecimal(v2)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            b1 = BigDecimal(0)
            b2 = BigDecimal(0)
        }
        return round(b1.subtract(b2), scale)
    }

    /**
     * 提供精确的乘法运算
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    fun mul(v1: String, v2: String): BigDecimal {
        var b1: BigDecimal
        var b2: BigDecimal
        try {
            b1 = BigDecimal(v1)
            b2 = BigDecimal(v2)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            b1 = BigDecimal(0)
            b2 = BigDecimal(0)
        }
        return b1.multiply(b2)
    }

    /**
     * 提供精确的乘法运算
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留scale 位小数
     * @return 两个参数的积
     */
    fun mul(v1: String, v2: String, scale: Int): BigDecimal {
        var b1: BigDecimal
        var b2: BigDecimal
        try {
            b1 = BigDecimal(v1)
            b2 = BigDecimal(v2)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            b1 = BigDecimal(0)
            b2 = BigDecimal(0)
        }
        return round(b1.multiply(b2), scale)
    }

    /**
     * 提供精确的除法运算。
     * @param v1    被除数
     * @param v2    除数
     * @return 两个参数的商
     */
    fun p(v1: String, v2: String): BigDecimal {
        var b1: BigDecimal
        var b2: BigDecimal
        try {
            b1 = BigDecimal(v1)
            b2 = BigDecimal(v2)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            b1 = BigDecimal(0)
            b2 = BigDecimal(0)
        }
        return b1.divide(b2)
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    fun p(v1: String, v2: String, scale: Int): BigDecimal {
        var b1: BigDecimal
        var b2: BigDecimal
        try {
            b1 = BigDecimal(v1)
            b2 = BigDecimal(v2)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            b1 = BigDecimal(0)
            b2 = BigDecimal(0)
        }
        return round(b1.divide(b2), scale)
    }

    /**
     * 取余数
     * @param v1    被除数
     * @param v2    除数
     * @param scale 小数点后保留几位
     * @return 余数
     */
    fun remainder(v1: String, v2: String, scale: Int): String {
        var b1: BigDecimal
        var b2: BigDecimal
        try {
            b1 = BigDecimal(v1)
            b2 = BigDecimal(v2)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            b1 = BigDecimal(0)
            b2 = BigDecimal(0)
        }
        return round(b1.remainder(b2), scale).toString()
    }

    /**
     * 提供精确的小数位四舍五入处理
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    fun round(b: BigDecimal, scale: Int): BigDecimal {
        return b.setScale(if (scale <= 0) 0 else scale, BigDecimal.ROUND_HALF_UP)
    }

    /**
     * 比较大小
     * @param v1 被比较数
     * @param v2 比较数
     * @return 如果v1 大于v2 则 返回true 否则false
     */
    fun compare(v1: String, v2: String): Boolean {
        var b1: BigDecimal
        var b2: BigDecimal
        try {
            b1 = BigDecimal(v1)
            b2 = BigDecimal(v2)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            b1 = BigDecimal(0)
            b2 = BigDecimal(0)
        }
        val bj = b1.compareTo(b2)
        return bj > 0
    }

}