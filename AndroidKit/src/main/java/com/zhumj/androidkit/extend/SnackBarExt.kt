package com.zhumj.androidkit.extend

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.setPadding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.zhumj.androidkit.R
import com.zhumj.androidkit.builder.ShapeBuilder

/**
 * @author Created by zhumj
 * @date 2022/5/3 10:12
 * @description : SnackBar 扩展，新增显示 Toast 样式的 SnackBar 的方法
 */
object SnackBarExt {

    enum class ToastType { NORMAL, SUCCESS, INFO, WARNING, ERROR }

    interface OnActionClickListener {
        fun onActionClick(snackBar: Snackbar)
    }

    /**
     * 创建 SnackBar
     * @param parent
     * @param text 显示文本
     * @param duration 显示时间，可自定义，毫秒
     */
    fun make(parent: View, text: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
        return Snackbar.make(parent, text, duration)
    }

    /**
     * 显示 Toast 样式的 SnackBar
     * @param textColor 文本颜色
     * @param textSize 文本大小
     * @param bgColor 背景色
     * @param toastType Toast 样式
     * @param iconId Icon Id
     * @param iconWidth Icon 宽度
     * @param iconHeight Icon 高度
     * @param radius 倒角半径
     * @param gravity 显示位置，默认 Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
     * @param actionText Action 文字，默认 “DISMISS”
     * @param actionTextColor Action 文字颜色
     * @param actionTextSize Action 文字大小
     * @param onActionListener Action 点击事件，当 duration == LENGTH_INDEFINITE && onActionListener != null 时，Action 才显示
     */
    @SuppressLint("InflateParams")
    fun Snackbar.showToast(
        @ColorInt textColor: Int = Color.parseColor("#FFFFFF"),
        textSize: Float = context.resources.getDimension(R.dimen.px_16),
        @ColorInt bgColor: Int? = null,
        toastType: ToastType = ToastType.NORMAL,
        @DrawableRes iconId: Int? = null,
        iconWidth: Int = context.resources.getDimensionPixelSize(R.dimen.dp_24),
        iconHeight: Int = context.resources.getDimensionPixelSize(R.dimen.dp_24),
        radius: Float = context.resources.getDimension(R.dimen.dp_6),
        gravity: Int = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
        actionText: String = "DISMISS",
        actionTextColor: Int = textColor,
        actionTextSize: Float = textSize,
        onActionListener: OnActionClickListener? = null,
    ) {
        // 背景颜色
        val mBgColor = bgColor ?: when(toastType) {
            ToastType.SUCCESS -> { Color.parseColor("#388E3C") }
            ToastType.INFO -> { Color.parseColor("#3F51B5") }
            ToastType.WARNING -> { Color.parseColor("#FFA900") }
            ToastType.ERROR -> { Color.parseColor("#D50000") }
            else -> { Color.parseColor("#353A3E") }
        }
        // Icon Id
        val mIconId = iconId ?: when (toastType) {
            ToastType.SUCCESS -> R.drawable.ic_success
            ToastType.INFO -> R.drawable.ic_info
            ToastType.WARNING -> R.drawable.ic_warning
            ToastType.ERROR -> R.drawable.ic_error
            else -> null
        }
        // 把设置文本取出来，等下要设置到自己的 View 中
        val textView: TextView = view.findViewById(com.google.android.material.R.id.snackbar_text)
        val text = textView.text
        // 设置 Behavior 为可以侧滑消除
        behavior = object : BaseTransientBottomBar.Behavior() {
            override fun canSwipeDismissView(child: View): Boolean {
                return false
            }
        }
        // 修改背景
        view.apply {
            this.minimumWidth = 0
            this.minimumHeight = 0
            this.setPadding(0)
            ShapeBuilder()
                .setShapeType(ShapeBuilder.ShapeType.RECTANGLE)
                .setShapeCornersRadius(radius)
                .setShapeSolidColor(mBgColor)
                .into(this)
        }
        // 修改显示位置
        val layoutParams = view.layoutParams
        if (layoutParams is CoordinatorLayout.LayoutParams) {
            layoutParams.gravity = gravity
        } else {
            (layoutParams as FrameLayout.LayoutParams).gravity = gravity
        }
        // 先显示，后面的自定义 View 才会生效
        show()
        // 创建并添加自定义 View
        (this.view as Snackbar.SnackbarLayout).also { layout: Snackbar.SnackbarLayout ->
            // 把 layout 里原先系统设置的 View 清空
            layout.removeAllViews()
            // 添加自定义 View
            layout.addView(LayoutInflater.from(context).inflate(R.layout.layout_toast, null).apply {
                // 设置背景颜色为透明，不然会遮挡上面设置的背景颜色
                this.setBackgroundColor(Color.TRANSPARENT)
                // 自定义左边 Icon
                this.findViewById<ImageView>(R.id.ivToast).also {
                    it.layoutParams.width = iconWidth
                    it.layoutParams.height = iconHeight
                    if (mIconId != null) {
                        it.visibility = View.VISIBLE
                        it.setImageResource(mIconId)
                    } else {
                        it.visibility = View.GONE
                    }
                }
                // 自定义中间文本
                this.findViewById<TextView>(R.id.tvToast).also {
                    it.setTextColor(textColor)
                    it.textSize = textSize
                    it.text = text
                }
                // 自定义右边 Action
                this.findViewById<Button>(R.id.btnToast).also {
                    if (duration == Snackbar.LENGTH_INDEFINITE && onActionListener != null) {
                        it.visibility = View.VISIBLE
                        it.text = actionText
                        it.textSize = actionTextSize
                        it.setTextColor(actionTextColor)
                        it.setOnClickListener{
                            onActionListener.onActionClick(this@showToast)
                        }
                    } else {
                        it.visibility = View.GONE
                        it.setOnClickListener(null)
                    }
                }
            })
        }
    }

}