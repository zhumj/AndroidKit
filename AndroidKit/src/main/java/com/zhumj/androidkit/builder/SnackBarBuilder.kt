package com.zhumj.androidkit.builder

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.view.setPadding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import com.zhumj.androidkit.R
import com.zhumj.androidkit.singleton.SingletonHolder
import java.lang.ref.WeakReference


/**
 * @author Created by zhumj
 * @date 2022/4/29 13:34
 * @description : SnackBar 构造器，单例模式，在 ToastBuilder 被 @Deprecated 标记后的替代品，用法和 ToastBuilder 差不多
 */
class SnackBarBuilder(private val context: Context) {

    companion object: SingletonHolder<SnackBarBuilder, Context>(::SnackBarBuilder)

    private var snackBarWeakReference: WeakReference<Snackbar>? = null

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
                    else -> { null }
                }
            }
        }
    private var iconWidth: Int = context.resources.getDimensionPixelSize(R.dimen.dp_24)
    private var iconHeight: Int = context.resources.getDimensionPixelSize(R.dimen.dp_24)
    //四个倒角半径
    private var radius: Float = context.resources.getDimension(R.dimen.dp_6)
    //显示时间
    private var duration: Int = Snackbar.LENGTH_SHORT
        get() {
            return when(field) {
                Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG, Snackbar.LENGTH_INDEFINITE -> field
                else -> if (field < 1000) 1000 else field
            }
        }

    /**
     * SnackBar 的按钮的一些配置
     * listener 跟 duration = Snackbar.LENGTH_INDEFINITE 配合决定按钮显不显示
     */
    data class ActionConfig(
        val actionText: String = "DISMISS",
        val actionTextColor: Int? = null,
        val actionTextSize: Float? = null,
        val listener: View.OnClickListener? = null
    )

    fun setToastType(toastType: ToastType): SnackBarBuilder {
        this.toastType = toastType
        return this
    }

    fun setBgColor(@ColorInt color: Int): SnackBarBuilder {
        this.bgColor = color
        return this
    }

    fun setMessage(message: String?): SnackBarBuilder {
        this.message = message
        return this
    }

    fun setTextColor(@ColorInt color: Int): SnackBarBuilder {
        this.textColor = color
        return this
    }

    fun setTextSize(size: Float): SnackBarBuilder {
        this.textSize = size
        return this
    }

    fun setIconId(@DrawableRes iconId: Int): SnackBarBuilder {
        this.iconId = iconId
        return this
    }

    fun setIconWidth(iconWidth: Int): SnackBarBuilder {
        this.iconWidth = iconWidth
        return this
    }

    fun setIconHeight(iconHeight: Int): SnackBarBuilder {
        this.iconHeight = iconHeight
        return this
    }

    fun setRadius(radius: Float): SnackBarBuilder {
        this.radius = radius
        return this
    }

    fun setDuration(duration: Int): SnackBarBuilder {
        this.duration = duration
        return this
    }

    /**
     * 显示系统纯正的 SnackBar
     */
    fun showSnackBar(parent: View, actionConfig: ActionConfig = ActionConfig()) {
        dismissSnackBar()
        snackBarWeakReference = WeakReference(Snackbar.make(parent, message ?: "", duration)).also {
            it.get()?.also { snackBar ->
                snackBar.view.also { v: View ->
                    ShapeBuilder()
                        .setShapeType(ShapeBuilder.ShapeType.RECTANGLE)
                        .setShapeCornersRadius(radius)
                        .setShapeSolidColor(bgColor!!)
                        .into(v)
                }
                if (duration == Snackbar.LENGTH_INDEFINITE && actionConfig.listener != null) {
                    snackBar.setAction(actionConfig.actionText, actionConfig.listener).setActionTextColor(actionConfig.actionTextColor ?: textColor)
                }
                snackBar.show()
            }
        }
    }

    /**
     * 显示自定义了 View 的 Toast 样式的 SnackBar
     */
    fun showToast(parent: View, actionConfig: ActionConfig = ActionConfig()) {
        dismissSnackBar()
        snackBarWeakReference = WeakReference(Snackbar.make(parent, message ?: "", duration)).also {
            it.get()?.also { snackBar ->
                snackBar.view.apply {
                    this.minimumWidth = 0
                    this.minimumHeight = 0
                    this.setPadding(0)
                    ShapeBuilder()
                        .setShapeType(ShapeBuilder.ShapeType.RECTANGLE)
                        .setShapeCornersRadius(radius)
                        .setShapeSolidColor(bgColor!!)
                        .into(this)
                }

                snackBar.show()

                (snackBar.view as SnackbarLayout).also { layout: SnackbarLayout ->
                    layout.removeAllViews()
                    createContentView(actionConfig).also { contentView: View ->
                        layout.addView(contentView)
                    }
                }
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun createContentView(actionConfig: ActionConfig): View {
        return LayoutInflater.from(context).inflate(R.layout.layout_toast, null).apply {
            this.setBackgroundColor(Color.TRANSPARENT)

            this.findViewById<TextView>(R.id.tvToast).also {
                it.setTextColor(textColor)
                it.textSize = textSize
                it.text = if (message.isNullOrBlank()) { "" } else { message }
            }

            this.findViewById<ImageView>(R.id.ivToast).also {
                it.layoutParams.width = iconWidth
                it.layoutParams.height = iconHeight
                if (iconId != null) {
                    it.setImageResource(iconId!!)
                } else {
                    it.visibility = View.GONE
                }
            }

            this.findViewById<Button>(R.id.btnToast).also {
                it.text = actionConfig.actionText
                it.textSize = actionConfig.actionTextSize ?: textSize
                it.setTextColor(actionConfig.actionTextColor ?: textColor)
                it.setOnClickListener(actionConfig.listener)
                if (duration == Snackbar.LENGTH_INDEFINITE && actionConfig.listener != null) {
                    it.visibility = View.VISIBLE
                } else {
                    it.visibility = View.GONE
                }
            }
        }
    }

    /**
     * 取消SnackBar显示
     */
    fun dismissSnackBar() {
        snackBarWeakReference?.get()?.dismiss()
        snackBarWeakReference = null
    }
    
}