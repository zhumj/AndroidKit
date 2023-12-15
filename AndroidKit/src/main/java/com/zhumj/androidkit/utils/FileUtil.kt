package com.zhumj.androidkit.utils

import android.content.Context
import android.text.format.Formatter
import java.io.File

/**
 * @Author Created by zhumj
 * @Date 2023/12/14 10:30
 * @Description 文件操作
 */
object FileUtil {

    /**
     * 删除指定文件夹或文件
     */
    fun deleteFileOrDir(file: File): Boolean {
        if (file.isDirectory) {
            val children = file.list()
            if (children != null && children.isNotEmpty()) {
                for (child in children) {
                    val success = deleteFileOrDir(File(file, child))
                    if (!success) {
                        return false
                    }
                }
            }
        }
        return file.delete()
    }

    /**
     * 获取文件夹/文件大小
     */
    fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            if (fileList != null) {
                for (value in fileList) {
                    // 如果下面还有文件
                    size = if (value.isDirectory) {
                        size + getFolderSize(value)
                    } else {
                        size + value.length()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * 格式化单位
     */
    fun getFormatSize(context: Context, fileSize: Long): String {
        return Formatter.formatFileSize(context, fileSize)
    }
}