package com.zhumj.androidkit.builder

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.zhumj.androidkit.R

/*
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
@IntDef(ToastType.NORMAL, ToastType.SUCCESS, ToastType.INFO, ToastType.WARNING, ToastType.ERROR)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class ToastType {
    companion object {
        const val NORMAL = 0
        const val SUCCESS = 1
        const val INFO = 2
        const val WARNING = 3
        const val ERROR = 4
    }
}
 */

/**
 * @author Created by zhumj
 * @date 2022/4/23 16:59
 * @description : 吐司构造器
 */
@Deprecated(message = "Android 11（API 30）之后 Toast 不能自定义了，请使用 SnackBarExt 里面的 Snackbar.showToast 方法代替")
class ToastBuilder(private val context: Context) {

    //Toast类型
    enum class ToastType { NORMAL, SUCCESS, INFO, WARNING, ERROR }
    private var toastType: ToastType =  ToastType.NORMAL
    //背景颜色
    @ColorInt
    private var bgColor: Int? = null
        get() {
            return if (field != null) {
                field
            } else {
                when (toastType) {
                    ToastType.SUCCESS -> { Color.parseColor("#388E3C") }
                    ToastType.INFO -> { Color.parseColor("#3F51B5") }
                    ToastType.WARNING -> { Color.parseColor("#FFA900") }
                    ToastType.ERROR -> { Color.parseColor("#D50000") }
                    else -> { Color.parseColor("#353A3E") }
                }
            }
        }
    //提示信息
    private var message: String? = null
    //文本颜色
    @ColorInt
    private var textColor: Int = Color.parseColor("#FFFFFF")
    //字号大小
    private var textSize: Float = context.resources.getDimension(R.dimen.px_16)
    //图片
    @DrawableRes
    private var iconId: Int? = null
        get() {
            return if (field != null) {
                field
            } else {
                when (toastType) {
                    ToastType.SUCCESS -> { R.drawable.ic_success }
                    ToastType.INFO -> { R.drawable.ic_info }
                    ToastType.WARNING -> { R.drawable.ic_warning }
                    ToastType.ERROR -> { R.drawable.ic_error }
                    else -> { field }
                }
            }
        }
    private var iconWidth: Int = context.resources.getDimensionPixelSize(R.dimen.dp_24)
    private var iconHeight: Int = context.resources.getDimensionPixelSize(R.dimen.dp_24)
    //四个倒角半径
    private var radius: Float = context.resources.getDimension(R.dimen.dp_6)
    //Toast显示位置
    private var gravity: Int = Gravity.BOTTOM
    private var xOffset: Int = 0
    private var yOffset: Int = context.resources.getDimensionPixelSize(R.dimen.dp_45)
    //显示时间
    private var duration: Int = Toast.LENGTH_SHORT

    fun setToastType(toastType: ToastType): ToastBuilder {
        this.toastType = toastType
        return this
    }

    fun setBgColor(@ColorInt color: Int): ToastBuilder {
        this.bgColor = color
        return this
    }

    fun setMessage(message: String?): ToastBuilder {
        this.message = message
        return this
    }

    fun setMsgColor(@ColorInt color: Int): ToastBuilder {
        this.textColor = color
        return this
    }

    fun setMsgSize(size: Float): ToastBuilder {
        this.textSize = size
        return this
    }

    fun setIconId(@DrawableRes iconId: Int): ToastBuilder {
        this.iconId = iconId
        return this
    }

    fun setIconWidth(iconWidth: Int): ToastBuilder {
        this.iconWidth = iconWidth
        return this
    }

    fun setIconHeight(iconHeight: Int): ToastBuilder {
        this.iconHeight = iconHeight
        return this
    }

    fun setRadius(radius: Float): ToastBuilder {
        this.radius = radius
        return this
    }

    fun setGravity(gravity: Int, xOffset: Int, yOffset: Int): ToastBuilder {
        this.gravity = gravity
        this.xOffset = xOffset
        this.yOffset = yOffset
        return this
    }

    fun setDuration(duration: Int): ToastBuilder {
        this.duration = duration
        return this
    }

    @SuppressLint("InflateParams")
    fun create(): Toast {
        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_toast, null).also {
            ShapeBuilder()
                .setShapeType(ShapeBuilder.ShapeType.RECTANGLE)
                .setShapeCornersRadius(radius)
                .setShapeSolidColor(bgColor!!)
                .into(it)

            it.findViewById<TextView>(R.id.tvToast).also { v: TextView ->
                v.setTextColor(textColor)
                v.textSize = textSize
                if (message.isNullOrBlank()) {
                    v.visibility = View.GONE
                } else {
                    v.text = message
                }
            }

            it.findViewById<ImageView>(R.id.ivToast).also { imageView ->
                imageView.layoutParams.height = iconHeight
                imageView.layoutParams.width = iconWidth
                if (iconId != null) {
                    imageView.visibility = View.VISIBLE
                    imageView.setImageResource(iconId!!)
                } else {
                    imageView.visibility = View.GONE
                }
            }

            it.findViewById<Button>(R.id.btnToast).also { button ->
                button.visibility = View.GONE
            }
        }

        return Toast(context).also {
            it.view = contentView
            it.duration = duration
            it.setGravity(gravity, xOffset, yOffset)
        }
    }
}