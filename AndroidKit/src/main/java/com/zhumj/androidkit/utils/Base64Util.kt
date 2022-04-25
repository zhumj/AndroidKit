package com.zhumj.androidkit.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @author Created by zhumj
 * @date 2022/4/23 14:38
 * @description : Base64 编解码相关工具类
 */
object Base64Util {

    /**
     * 对数据进行编码
     */
    fun encodeToString(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    /**
     * 对Base64字符串进行解码
     */
    fun decode(base64Str: String): ByteArray {
        return Base64.decode(base64Str, Base64.DEFAULT)
    }

    /**
     * 对字符串进行Base64编码
     */
    fun stringEncode(string: String): String {
        return encodeToString(string.toByteArray())
    }

    /**
     * 对Base64字符串进行解码
     */
    fun stringDecode(base64Str: String): String {
        return decode(base64Str).toString()
    }

    /**
     * 对文件进行Base64编码
     */
    fun fileEncode(file: File): String? {
        val inputFile: FileInputStream?
        try {
            inputFile = FileInputStream(file)
            val buffer = ByteArray(file.length().toInt())
            inputFile.read(buffer)
            inputFile.close()
            return encodeToString(buffer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 对文件进行Base64解码
     */
    fun fileDecode(base64Str: String, savePath: String): File? {
        val desFile = File(savePath)
        val fos: FileOutputStream?
        try {
            val decodeBytes = decode(base64Str)
            fos = FileOutputStream(desFile)
            fos.write(decodeBytes)
            fos.close()
            return desFile
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 对图片进行Base64编码
     */
    fun bitmapEncode(bitmap: Bitmap): String {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)//参数100表示不压缩
        val bytes = bos.toByteArray()
        return encodeToString(bytes)
    }

    /**
     * 对图片进行Base64解码
     */
    fun bitmapDecode(base64Str: String): Bitmap {
        val decodedString = decode(base64Str)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

}