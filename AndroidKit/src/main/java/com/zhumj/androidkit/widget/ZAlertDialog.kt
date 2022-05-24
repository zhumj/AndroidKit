package com.zhumj.androidkit.widget

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.zhumj.androidkit.R
import com.zhumj.androidkit.databinding.LayoutDialogBinding

/**
 * @Author Created by zhumj
 * @Date 2022/5/23 13:59
 * @Description 自定义 AlertDialog，旨在统一 Dialog 风格
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
        setCustomContentView(contentView)
        mViewBinding.btnCancel.setOnClickListener(this)
        mViewBinding.btnComplete.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        super.onCreate(savedInstanceState)
        setContentView(mViewBinding.root)
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

    /* 11111111111111111111111111111 RootView 11111111111111111111111111111 */
    fun changeRootViewParam(obj: (CardView) -> Unit): ZAlertDialog {
        obj(mViewBinding.rootCardView)
        return this
    }

    /* 11111111111111111111111111111 标题 11111111111111111111111111111 */
    fun changeTitleViewParam(obj: (TextView) -> Unit): ZAlertDialog {
        obj(mViewBinding.tvTitle)
        return this
    }

    /*11111111111111111111111111111 默认文本内容 11111111111111111111111111111 */
    fun changeMessageViewParam(obj: (TextView) -> Unit): ZAlertDialog {
        obj(mViewBinding.tvMessage)
        return this
    }

    /* 11111111111111111111111111111 自定义内容 11111111111111111111111111111 */
    fun setCustomContentView(contentView: View?): ZAlertDialog {
        if (contentView != null) {
            mViewBinding.flContentView.removeAllViews()
            mViewBinding.flContentView.addView(contentView)
        }
        return this
    }

    /* 11111111111111111111111111111 全部操作按钮 11111111111111111111111111111 */
    fun changeButtonRootViewParam(obj: (LinearLayout) -> Unit): ZAlertDialog {
        obj(mViewBinding.llButtonParent)
        return this
    }

    /* 11111111111111111111111111111 取消按钮 11111111111111111111111111111 */
    fun changeCancelButtonParam(obj: ((Button) -> Unit)? = null, isClickCancelAutoDismiss: Boolean = true, listener: OnZAlertDialogButtonClickListener? = null): ZAlertDialog {
        obj?.let {
            it(mViewBinding.btnCancel)
            setButtonDividingLineVisibility()
        }
        this.isClickCancelAutoDismiss = isClickCancelAutoDismiss
        onCancelClickListener = listener
        return this
    }

    /* 11111111111111111111111111111 确认按钮 11111111111111111111111111111 */
    fun changeCompleteButtonParam(obj: ((Button) -> Unit)? = null, isClickCompleteAutoDismiss: Boolean = true, listener: OnZAlertDialogButtonClickListener? = null): ZAlertDialog {
        obj?.let {
            it(mViewBinding.btnComplete)
            setButtonDividingLineVisibility()
        }
        this.isClickCompleteAutoDismiss = isClickCompleteAutoDismiss
        onCompleteClickListener = listener
        return this
    }

    private fun setButtonDividingLineVisibility() {
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