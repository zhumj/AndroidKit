package com.zhumj.androidkit.utils

import android.content.*
import android.net.Uri

/**
 * @author Created by zhumj
 * @date 2022/4/29 8:15
 * @description : 剪切板相关工具类
 */
object ClipboardUtil {

    private fun getDefaultLabel(context: Context): String {
        return AppUtil.getAppName(context) ?: context.packageName
    }

    /**
     * 复制字符串到剪切板
     */
    fun copyText(context: Context, text: String) {
        copyText(context, getDefaultLabel(context), text)
    }
    fun copyText(context: Context, label: String, text: String) {
        try {
            val clipboardManager = context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val mClipData = ClipData.newPlainText(label, text)
            clipboardManager.setPrimaryClip(mClipData)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 复制 Html 字符串到剪切板
     */
    fun copyHtmlText(context: Context, text: String, htmlText: String) {
        copyHtmlText(context, getDefaultLabel(context), text, htmlText)
    }
    fun copyHtmlText(context: Context, label: String, text: String, htmlText: String) {
        try {
            val clipboardManager = context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val mClipData = ClipData.newHtmlText(label, text, htmlText)
            clipboardManager.setPrimaryClip(mClipData)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 复制 Uri 到剪切板
     */
    fun copyUri(context: Context, uri: Uri) {
        copyUri(context, getDefaultLabel(context), uri)
    }
    fun copyUri(context: Context, label: String, uri: Uri) {
        try {
            val clipboardManager = context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val mClipData = ClipData.newUri(context.contentResolver, label, uri)
            clipboardManager.setPrimaryClip(mClipData)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 复制 RawUri 到剪切板
     */
    fun copyRawUri(context: Context, uri: Uri) {
        copyRawUri(context, getDefaultLabel(context), uri)
    }
    fun copyRawUri(context: Context, label: String, uri: Uri) {
        try {
            val clipboardManager =
                context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val mClipData = ClipData.newRawUri(label, uri)
            clipboardManager.setPrimaryClip(mClipData)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 复制 Intent 到剪切板
     */
    fun copyIntent(context: Context, intent: Intent) {
        copyIntent(context, getDefaultLabel(context), intent)
    }
    fun copyIntent(context: Context, label: String, intent: Intent) {
        try {
            val clipboardManager = context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val mClipData = ClipData.newIntent(label, intent)
            clipboardManager.setPrimaryClip(mClipData)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取剪切板上的内容，不管什么类型都强制转成String
     */
    fun getClipboardContent(context: Context): String? {
        try {
            val cm: ClipboardManager = context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            if (cm.hasPrimaryClip()) {
                val clipData: ClipData? = cm.primaryClip
                if (clipData != null && clipData.itemCount > 0) {
                    val item = clipData.getItemAt(0)
                    if (item != null) {
                        //清空剪贴板，一般获取完数据之后需要清空
                        cm.setPrimaryClip(ClipData.newPlainText(null,null))
                    }
                    return item?.coerceToText(context)?.toString()
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取第一条内容类型
     */
    enum class ClipType { UNKNOWN, TEXT, INTENT, URI, HTML, }
    fun getClipType(context: Context): ClipType {
        try {
            val cm: ClipboardManager = context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            if (cm.hasPrimaryClip()) {
                val clipDescription = cm.primaryClipDescription
                if (clipDescription != null && clipDescription.mimeTypeCount > 0) {
                    clipDescription.getMimeType(0).also {
                        return when (it) {
                            ClipDescription.MIMETYPE_TEXT_PLAIN -> ClipType.TEXT
                            ClipDescription.MIMETYPE_TEXT_INTENT -> ClipType.INTENT
                            ClipDescription.MIMETYPE_TEXT_URILIST -> ClipType.URI
                            ClipDescription.MIMETYPE_TEXT_HTML -> ClipType.HTML
                            else -> ClipType.UNKNOWN
                        }
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return ClipType.UNKNOWN
    }

}