package com.zhumj.androidkit.widget

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.zhumj.androidkit.R
import com.zhumj.androidkit.databinding.LayoutDialogBinding

/**
 * @Author Created by zhumj
 * @Date 2022/5/23 13:59
 * @Description 文件描述
 */
class ZAlertDialog(context: Context, contentView: View? = null) : AlertDialog(context), View.OnClickListener {

    private var mViewBinding: LayoutDialogBinding

    /**
     * 判断是否点击取消按钮时自动 dismiss
     */
    private var isClickCancelAutoDismiss = true

    /**
     * 判断是否点击确认按钮时自动 dismiss
     */
    private var isClickCompleteAutoDismiss = true

    private var onCancelClickListener: OnZAlertDialogButtonClickListener? = null
    private var onCompleteClickListener: OnZAlertDialogButtonClickListener? = null

    init {
        mViewBinding = LayoutDialogBinding.inflate(LayoutInflater.from(context))
        setZAlertDialogContentView(contentView)
        mViewBinding.btnCancel.setOnClickListener(this)
        mViewBinding.btnComplete.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)

        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.clearFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE 
                    or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
        )
    }

    override fun onClick(v: View) {
        val viewId = v.id
        if (viewId == R.id.btnCancel) {
            onCancelClickListener?.onDialogButtonClick(this, v)
            if (isClickCancelAutoDismiss) {
                dismiss()
            }
        } else if (viewId == R.id.btnComplete) {
            onCompleteClickListener?.onDialogButtonClick(this, v)
            if (isClickCompleteAutoDismiss) {
                dismiss()
            }
        }
    }

    fun getZAlertDialogRootView(): CardView {
        return mViewBinding.rootCardView
    }

    fun getZAlertDialogTitleView(): TextView {
        return mViewBinding.tvTitle
    }

    fun getZAlertDialogMessageView(): TextView {
        return mViewBinding.tvMessage
    }

    fun getZAlertDialogContentView(): FrameLayout {
        return mViewBinding.flContentView
    }

    fun getZAlertDialogCancelButton(): Button {
        return mViewBinding.btnCancel
    }

    fun getZAlertDialogCompleteButton(): Button {
        return mViewBinding.btnComplete
    }

    /* 11111111111111111111111111111 CardView背景 11111111111111111111111111111 */
    fun setZAlertDialogBackgroundColor(@ColorInt color: Int): ZAlertDialog {
        mViewBinding.rootCardView.setCardBackgroundColor(color)
        return this
    }

    /* 11111111111111111111111111111 自定义内容 11111111111111111111111111111 */
    fun setZAlertDialogContentView(contentView: View?): ZAlertDialog {
        if (contentView != null) {
            mViewBinding.flContentView.removeAllViews()
            mViewBinding.flContentView.addView(contentView)
        }
        return this
    }

    /*11111111111111111111111111111 文本内容 11111111111111111111111111111 */
    fun setZAlertDialogMessage(@StringRes resId: Int): ZAlertDialog {
        mViewBinding.tvMessage.setText(resId)
        return this
    }

    fun setZAlertDialogMessage(text: String?): ZAlertDialog {
        mViewBinding.tvMessage.text = text
        return this
    }

    fun setZAlertDialogMessageSize(spSize: Float): ZAlertDialog {
        return setZAlertDialogMessageSize(TypedValue.COMPLEX_UNIT_SP, spSize)
    }

    fun setZAlertDialogMessageSize(unit: Int, size: Float): ZAlertDialog {
        mViewBinding.tvMessage.setTextSize(unit, size)
        return this
    }

    fun setZAlertDialogMessageColor(@ColorInt color: Int): ZAlertDialog {
        mViewBinding.tvMessage.setTextColor(color)
        return this
    }

    fun setZAlertDialogMessageColor(colors: ColorStateList?): ZAlertDialog {
        mViewBinding.tvMessage.setTextColor(colors)
        return this
    }

    fun setZAlertDialogMessageGravity(gravity: Int): ZAlertDialog {
        mViewBinding.tvMessage.gravity = gravity
        return this
    }

    /* 11111111111111111111111111111 标题 11111111111111111111111111111 */
    fun setZAlertDialogTitleVisibility(visibility: Int): ZAlertDialog {
        mViewBinding.tvTitle.visibility = visibility
        return this
    }

    fun setZAlertDialogTitle(@StringRes resId: Int): ZAlertDialog {
        mViewBinding.tvTitle.setText(resId)
        return this
    }

    fun setZAlertDialogTitle(text: String?): ZAlertDialog {
        mViewBinding.tvTitle.text = text
        return this
    }

    fun setZAlertDialogTitleTextSize(spSize: Float): ZAlertDialog {
        return setZAlertDialogTitleTextSize(TypedValue.COMPLEX_UNIT_SP, spSize)
    }

    fun setZAlertDialogTitleTextSize(unit: Int, size: Float): ZAlertDialog {
        mViewBinding.tvTitle.setTextSize(unit, size)
        return this
    }

    fun setZAlertDialogTitleTextColor(@ColorInt color: Int): ZAlertDialog {
        mViewBinding.tvTitle.setTextColor(color)
        return this
    }

    fun setZAlertDialogTitleTextColor(colors: ColorStateList?): ZAlertDialog {
        mViewBinding.tvTitle.setTextColor(colors)
        return this
    }

    fun setZAlertDialogTitleTextGravity(gravity: Int): ZAlertDialog {
        mViewBinding.tvTitle.gravity = gravity
        return this
    }

    /* 11111111111111111111111111111 全部操作按钮 11111111111111111111111111111 */
    fun setZAlertDialogButtonVisibility(visibility: Int): ZAlertDialog {
        mViewBinding.llButtonParent.visibility = visibility
        return this
    }

    /* 11111111111111111111111111111 取消按钮 11111111111111111111111111111 */
    fun setZAlertDialogClickCancelAutoDismiss(clickCancelAutoDismiss: Boolean): ZAlertDialog {
        isClickCancelAutoDismiss = clickCancelAutoDismiss
        return this
    }

    fun setZAlertDialogCancelButtonText(@StringRes resId: Int): ZAlertDialog {
        mViewBinding.btnCancel.setText(resId)
        return this
    }

    fun setZAlertDialogCancelButtonText(text: String?): ZAlertDialog {
        mViewBinding.btnCancel.text = text
        return this
    }

    fun setZAlertDialogCancelButtonSize(spSize: Float): ZAlertDialog {
        return setZAlertDialogCancelButtonSize(TypedValue.COMPLEX_UNIT_SP, spSize)
    }

    fun setZAlertDialogCancelButtonSize(unit: Int, size: Float): ZAlertDialog {
        mViewBinding.btnCancel.setTextSize(unit, size)
        return this
    }

    fun setZAlertDialogCancelButtonTextColor(@ColorInt color: Int): ZAlertDialog {
        mViewBinding.btnCancel.setTextColor(color)
        return this
    }

    fun setZAlertDialogCancelButtonTextColor(colors: ColorStateList?): ZAlertDialog {
        mViewBinding.btnCancel.setTextColor(colors)
        return this
    }

    fun setZAlertDialogCancelButtonVisibility(visibility: Int): ZAlertDialog {
        mViewBinding.btnCancel.visibility = visibility
        setZAlertDialogLine1Visibility()
        return this
    }

    fun setZAlertDialogCancelButtonClickListener(listener: OnZAlertDialogButtonClickListener?): ZAlertDialog {
        onCancelClickListener = listener
        return this
    }

    /* 11111111111111111111111111111 确认按钮 11111111111111111111111111111 */
    fun setZAlertDialogClickCompleteAutoDismiss(clickCompleteAutoDismiss: Boolean): ZAlertDialog {
        isClickCompleteAutoDismiss = clickCompleteAutoDismiss
        return this
    }

    fun setZAlertDialogCompleteButtonText(@StringRes resId: Int): ZAlertDialog {
        mViewBinding.btnComplete.setText(resId)
        return this
    }

    fun setZAlertDialogCompleteButtonText(text: String?): ZAlertDialog {
        mViewBinding.btnComplete.text = text
        return this
    }

    fun setZAlertDialogCompleteButtonSize(spSize: Float): ZAlertDialog {
        return setZAlertDialogCompleteButtonSize(TypedValue.COMPLEX_UNIT_SP, spSize)
    }

    fun setZAlertDialogCompleteButtonSize(unit: Int, size: Float): ZAlertDialog {
        mViewBinding.btnComplete.setTextSize(unit, size)
        return this
    }

    fun setZAlertDialogCompleteButtonTextColor(@ColorInt color: Int): ZAlertDialog {
        mViewBinding.btnComplete.setTextColor(color)
        return this
    }

    fun setZAlertDialogCompleteButtonTextColor(colors: ColorStateList?): ZAlertDialog {
        mViewBinding.btnComplete.setTextColor(colors)
        return this
    }

    fun setZAlertDialogCompleteButtonVisibility(visibility: Int): ZAlertDialog {
        mViewBinding.btnComplete.visibility = visibility
        setZAlertDialogLine1Visibility()
        return this
    }

    fun setZAlertDialogCompleteButtonClickListener(listener: OnZAlertDialogButtonClickListener?): ZAlertDialog {
        onCompleteClickListener = listener
        return this
    }

    private fun setZAlertDialogLine1Visibility() {
        if (mViewBinding.btnCancel.visibility != View.VISIBLE
            || mViewBinding.btnComplete.visibility != View.VISIBLE
        ) {
            mViewBinding.vButtonDividing.visibility = View.GONE
        } else {
            mViewBinding.vButtonDividing.visibility = View.VISIBLE
        }
    }

    interface OnZAlertDialogButtonClickListener {
        fun onDialogButtonClick(dialog: ZAlertDialog, view: View)
    }

}