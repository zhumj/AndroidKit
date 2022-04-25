package com.zhumj.androidkit.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.AppCompatEditText

/**
 * @author Created by zhumj
 * @date 2022/4/23
 * @description : 自定义EditText，DrawableRight图片添加一键清除功能，添加文本抖动动画，可设置编辑状态
 */
class CustomEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs), View.OnFocusChangeListener, TextWatcher {

    /**
     * 删除按钮的引用
     */
    private var mClearDrawable: Drawable? = null

    /**
     * 控件是否有焦点
     */
    private var hasFocus: Boolean = false

    /**
     * 是否可以编辑
     */
    private var isEditable = true

    init {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = compoundDrawables[2]
        mClearDrawable?.setBounds(0, 0, mClearDrawable?.intrinsicWidth ?: 1, mClearDrawable?.intrinsicHeight ?: 1)
        //默认设置隐藏图标
        setClearIconVisible(false)
        //设置焦点改变的监听
        onFocusChangeListener = this
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this)
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        this.hasFocus = hasFocus
        if (hasFocus) {
            setClearIconVisible(text?.isNotEmpty() == true)
        } else {
            setClearIconVisible(false)
        }
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        if (hasFocus) setClearIconVisible(text.isNotEmpty())
    }

    override fun afterTextChanged(s: Editable?) { }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mClearDrawable != null && event.action == MotionEvent.ACTION_UP) {
            val x = event.x.toInt()
            //判断触摸点是否在水平范围内
            val isInnerWidth = x > width - totalPaddingRight && x < width - paddingRight
            //获取删除图标的边界，返回一个Rect对象
            val rect = mClearDrawable?.bounds
            //获取删除图标的高度
            val tHeight = rect?.height() ?: 0
            val y = event.y.toInt()
            //计算图标底部到控件底部的距离
            val distance = (height - tHeight) / 2
            //判断触摸点是否在竖直范围内(可能会有点误差)
            //触摸点的纵坐标在distance到（distance+图标自身的高度）之内，则视为点中删除图标
            val isInnerHeight = y > distance && y < distance + tHeight
            if (isInnerHeight && isInnerWidth) {
                this.setText("")
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    private fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], right, compoundDrawables[3])
    }

    /**
     * 晃动动画
     * @param duration 晃动时间，单位：毫秒
     * @param counts duration时间内晃动次数
     * @param fromXDelta 晃动起始X点
     * @param toXDelta 晃动结束X点
     * @param fromYDelta 晃动起始Y点
     * @param toYDelta 晃动结束Y点
     * @return
     */
    fun startShakeAnimation(duration: Long = 500, counts: Int = 3, fromXDelta: Float = 0f, toXDelta: Float = 5f, fromYDelta: Float = 0f, toYDelta: Float = 0f) {
        requestFocus()

        val translateAnimation = TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta)
        translateAnimation.interpolator = CycleInterpolator(counts.toFloat())
        translateAnimation.duration = duration
        this.startAnimation(translateAnimation)
    }

    fun setTextContent(text: CharSequence) {
        if (!isEditable) setEditable(true)
        super.setText(text, BufferType.NORMAL)
    }

    fun getEditable(): Boolean = isEditable

    fun setEditable(editable: Boolean) {
        this.isEditable = editable
        isEnabled = editable
        isFocusable = editable
        isFocusableInTouchMode = editable
        isLongClickable = editable
        isClickable = editable
        requestFocus()
        requestFocusFromTouch()
        if (editable) unLock() else lock()
    }

    //让EditText变成可编辑状态
    private fun unLock() {
        filters = arrayOf(InputFilter { _, _, _, _, _, _ -> null })
    }

    //让EditText变成不可编辑状态
    private fun lock() {
        filters = arrayOf(InputFilter { source, _, _, dest, dstart, dend ->
            if (source.isEmpty())
                dest.subSequence(dstart, dend)
            else
                ""
        })
    }
}