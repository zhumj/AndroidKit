package com.zhumj.androidkit.builder

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.core.view.ViewCompat

/**
 * @author Created by zhumj
 * @date 2022/4/23
 * @description shape构造器，使用这个可以直接代码设置 shape 和 selector
 */
/**
 * shape的样式
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@IntDef(ShapeType.TYPE_RECTANGLE, ShapeType.TYPE_OVAL, ShapeType.TYPE_LINE, ShapeType.TYPE_RING)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class ShapeType {
    companion object {
        const val TYPE_RECTANGLE = 0
        const val TYPE_OVAL = 1
        const val TYPE_LINE = 2
        const val TYPE_RING = 3
    }
}


@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER)
@IntDef(GradientType.TYPE_LINEAR, GradientType.TYPE_RADIAL, GradientType.TYPE_SWEEP)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class GradientType {
    companion object {
        const val TYPE_LINEAR = 4
        const val TYPE_RADIAL = 5
        const val TYPE_SWEEP = 6
    }
}

class ShapeBuilder {

    /**
     * android:shape=["rectangle" | "oval" | "line" | "ring"]
     */
    @ShapeType
    private var shapeType = ShapeType.TYPE_RECTANGLE

    /**
     * <solid android:color="color"></solid>
     */
    @ColorInt
    private var solidColor = -1

    /**
     * <stroke android:width="integer" android:color="color" android:dashWidth="integer" android:dashGap="integer"></stroke>
     */
    private var strokeWidth = -1
    @ColorInt
    private var strokeColor = -1
    private var strokeDashWidth = 0.0f
    private var strokeDashGap = 0.0f

    /**
     * <corners android:radius="integer" android:topLeftRadius="integer" android:topRightRadius="integer" android:bottomLeftRadius="integer" android:bottomRightRadius="integer"></corners>
     */
    private var cornersRadius = 0.0f
    private var cornersTopLeftRadius = 0.0f
    private var cornersTopRightRadius = 0.0f
    private var cornersBottomLeftRadius = 0.0f
    private var cornersBottomRightRadius = 0.0f

    private var gradientAngle = -1
    private var gradientCenterX: Int = 0
    private var gradientCenterY: Int = 0
    private var gradientGradientRadius: Int = 0

    @ColorInt
    private var gradientStartColor = -1
    @ColorInt
    private var gradientCenterColor = -1
    @ColorInt
    private var gradientEndColor = -1
    @GradientType
    private var gradientType: Int = GradientType.TYPE_LINEAR

    private var gradientUseLevel: Boolean = false

    /**
     * <size android:width="integer" android:height="integer"></size>
     */
    private var sizeWidth = -1
    private var sizeHeight = -1

    /**
     * <selector xmlns:android="http://schemas.android.com/apk/res/android" android:constantSize=["true" |></selector> "false"]
     * android:dither=["true" | "false"]
     * android:variablePadding=["true" | "false"] >
     * <item android:drawable="@[package:]drawable/drawable_resource" android:state_pressed=["true" |></item> "false"]
     * android:state_focused=["true" | "false"]
     * android:state_hovered=["true" | "false"]
     * android:state_selected=["true" | "false"]
     * android:state_checkable=["true" | "false"]
     * android:state_checked=["true" | "false"]
     * android:state_enabled=["true" | "false"]
     * android:state_activated=["true" | "false"]
     * android:state_window_focused=["true" | "false"] />
     *
     */
    private var selectorPressedColor: Int = 0
    private var selectorDisableColor: Int = 0
    private var selectorNormalColor: Int = 0

    private var useSelector: Boolean = false

    fun setShapeType(@ShapeType shapeType: Int): ShapeBuilder {
        this.shapeType = shapeType
        return this
    }

    fun setShapeSolidColor(@ColorInt color: Int): ShapeBuilder {
        this.solidColor = color
        return this
    }

    fun setShapeCornersRadius(radius: Float): ShapeBuilder {
        this.cornersRadius = radius
        return this
    }

    fun setShapeCornersTopLeftRadius(radius: Float): ShapeBuilder {
        this.cornersTopLeftRadius = radius
        return this
    }

    fun setShapeCornersTopRightRadius(radius: Float): ShapeBuilder {
        this.cornersTopRightRadius = radius
        return this
    }

    fun setShapeCornersBottomRightRadius(radius: Float): ShapeBuilder {
        this.cornersBottomRightRadius = radius
        return this
    }

    fun setShapeCornersBottomLeftRadius(radius: Float): ShapeBuilder {
        this.cornersBottomLeftRadius = radius
        return this
    }

    fun setShapeStrokeWidth(strokeWidth: Int): ShapeBuilder {
        this.strokeWidth = strokeWidth
        return this
    }

    fun setShapeStrokeColor(@ColorInt strokeColor: Int): ShapeBuilder {
        this.strokeColor = strokeColor
        return this
    }

    fun setShapeStrokeDashWidth(strokeDashWidth: Float): ShapeBuilder {
        this.strokeDashWidth = strokeDashWidth
        return this
    }

    fun setShapeStrokeDashGap(strokeDashGap: Float): ShapeBuilder {
        this.strokeDashGap = strokeDashGap
        return this
    }

    fun setShapeUseSelector(useSelector: Boolean): ShapeBuilder {
        this.useSelector = useSelector
        return this
    }

    fun setShapeSelectorPressedColor(@ColorInt color: Int): ShapeBuilder {
        this.selectorPressedColor = color
        return this
    }

    fun setShapeSelectorNormalColor(@ColorInt color: Int): ShapeBuilder {
        this.selectorNormalColor = color
        return this
    }

    fun setShapeSelectorDisableColor(@ColorInt color: Int): ShapeBuilder {
        this.selectorDisableColor = color
        return this
    }

    fun setShapeSizeWidth(sizeWidth: Int): ShapeBuilder {
        this.sizeWidth = sizeWidth
        return this
    }

    fun setShapeSizeHeight(sizeHeight: Int): ShapeBuilder {
        this.sizeHeight = sizeHeight
        return this
    }

    fun setShapeGradientAngle(gradientAngle: Int): ShapeBuilder {
        this.gradientAngle = gradientAngle
        return this
    }

    fun setShapeGradientCenterX(gradientCenterX: Int): ShapeBuilder {
        this.gradientCenterX = gradientCenterX
        return this
    }

    fun setShapeGradientCenterY(gradientCenterY: Int): ShapeBuilder {
        this.gradientCenterY = gradientCenterY
        return this
    }

    fun setShapeGradientGradientRadius(gradientGradientRadius: Int): ShapeBuilder {
        this.gradientGradientRadius = gradientGradientRadius
        return this
    }

    fun setShapeGradientStartColor(@ColorInt gradientStartColor: Int): ShapeBuilder {
        this.gradientStartColor = gradientStartColor
        return this
    }

    fun setShapeGradientCenterColor(@ColorInt gradientCenterColor: Int): ShapeBuilder {
        this.gradientCenterColor = gradientCenterColor
        return this
    }

    fun setShapeGradientEndColor(@ColorInt gradientEndColor: Int): ShapeBuilder {
        this.gradientEndColor = gradientEndColor
        return this
    }

    fun setShapeGradientType(@GradientType gradientType: Int): ShapeBuilder {
        this.gradientType = gradientType
        return this
    }

    fun setShapeGradientUseLevel(gradientUseLevel: Boolean): ShapeBuilder {
        this.gradientUseLevel = gradientUseLevel
        return this
    }

    private fun setShapeType(gradientDrawable: GradientDrawable) {
        when (shapeType) {
            ShapeType.TYPE_RECTANGLE -> gradientDrawable.shape = GradientDrawable.RECTANGLE
            ShapeType.TYPE_OVAL -> gradientDrawable.shape = GradientDrawable.OVAL
            ShapeType.TYPE_LINE -> gradientDrawable.shape = GradientDrawable.LINE
            ShapeType.TYPE_RING -> gradientDrawable.shape = GradientDrawable.RING
        }
    }


    private fun setSize(gradientDrawable: GradientDrawable) {
        if (sizeWidth > 0 || sizeHeight > 0) {
            gradientDrawable.setSize(sizeWidth, sizeHeight)
        }
    }

    /**
     * 设置边框  宽度  颜色  虚线  间隙
     */
    private fun setBorder(gradientDrawable: GradientDrawable) {
        if (strokeWidth >= 0) {
            gradientDrawable.setStroke(strokeWidth, strokeColor, strokeDashWidth, strokeDashGap)
        }
    }

    /**
     * 只有类型是矩形的时候设置圆角半径才有效
     */
    private fun setRadius(gradientDrawable: GradientDrawable) {
        if (shapeType == ShapeType.TYPE_RECTANGLE) {
            if (cornersRadius != 0f) {
                gradientDrawable.cornerRadius = cornersRadius
            } else {
                if (cornersTopLeftRadius != 0f || cornersTopRightRadius != 0f || cornersBottomRightRadius != 0f || cornersBottomLeftRadius != 0f) {
                    //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
                    gradientDrawable.cornerRadii = floatArrayOf(cornersTopLeftRadius, cornersTopLeftRadius, cornersTopRightRadius, cornersTopRightRadius, cornersBottomRightRadius, cornersBottomRightRadius, cornersBottomLeftRadius, cornersBottomLeftRadius)
                }
            }
        }
    }


    private fun setSolidColor(gradientDrawable: GradientDrawable) {
        if (gradientStartColor == -1 && gradientEndColor == -1) {
            gradientDrawable.setColor(solidColor)
        }
    }


    /**
     * 设置Selector的不同状态的颜色
     *
     * @param state 按钮状态
     */
    private fun setSelectorColor(gradientDrawable: GradientDrawable, state: Int) {
        if (useSelector && state != 0) {
            when (state) {
                android.R.attr.state_pressed -> gradientDrawable.setColor(selectorPressedColor)
                -android.R.attr.state_enabled -> gradientDrawable.setColor(selectorDisableColor)
                android.R.attr.state_enabled -> gradientDrawable.setColor(selectorNormalColor)
            }
        }
    }


    /**
     * 设置背景颜色
     * 如果设定的有Orientation 就默认为是渐变色的Button，否则就是纯色的Button
     */
    private fun setGradient(gradientDrawable: GradientDrawable) {
        if (gradientStartColor != -1 || gradientEndColor != -1) {
            if (gradientCenterColor == -1) {
                gradientDrawable.colors = intArrayOf(gradientStartColor, gradientEndColor)
            } else {
                gradientDrawable.colors = intArrayOf(gradientStartColor, gradientCenterColor, gradientEndColor)
            }
            when (gradientType) {
                GradientType.TYPE_LINEAR -> {
                    gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
                    if (gradientAngle != -1) {
                        gradientDrawable.orientation = getGradientOrientationByAngle(gradientAngle)
                    }
                }
                GradientType.TYPE_RADIAL -> {
                    gradientDrawable.gradientType = GradientDrawable.RADIAL_GRADIENT
                    gradientDrawable.gradientRadius = gradientGradientRadius.toFloat()
                }
                GradientType.TYPE_SWEEP -> gradientDrawable.gradientType = GradientDrawable.SWEEP_GRADIENT
            }
            if (gradientCenterX != 0 || gradientCenterY != 0) {
                gradientDrawable.setGradientCenter(gradientCenterX.toFloat(), gradientCenterY.toFloat())
            }
            gradientDrawable.useLevel = gradientUseLevel
        }
    }


    /**
     * 设置颜色渐变类型
     *
     * @param gradientAngle gradientAngle
     * @return Orientation
     */
    private fun getGradientOrientationByAngle(gradientAngle: Int): GradientDrawable.Orientation? {
        var orientation: GradientDrawable.Orientation? = null
        when (gradientAngle) {
            0 -> orientation = GradientDrawable.Orientation.LEFT_RIGHT
            45 -> orientation = GradientDrawable.Orientation.BL_TR
            90 -> orientation = GradientDrawable.Orientation.BOTTOM_TOP
            135 -> orientation = GradientDrawable.Orientation.BR_TL
            180 -> orientation = GradientDrawable.Orientation.RIGHT_LEFT
            225 -> orientation = GradientDrawable.Orientation.TR_BL
            270 -> orientation = GradientDrawable.Orientation.TOP_BOTTOM
            315 -> orientation = GradientDrawable.Orientation.TL_BR
        }
        return orientation
    }

    /**
     * 获取设置之后的Selector
     * 注意该处的顺序，只要有一个状态与之相配，背景就会被换掉 所以不要把大范围放在前面了，
     * 如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
     * @return stateListDrawable
     */
    private val selectorDrawable: StateListDrawable
        get() {
            val stateListDrawable = StateListDrawable()
            stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled), getDrawable(android.R.attr.state_pressed))
            stateListDrawable.addState(intArrayOf(-android.R.attr.state_enabled), getDrawable(-android.R.attr.state_enabled))
            stateListDrawable.addState(intArrayOf(), getDrawable(android.R.attr.state_enabled))
            return stateListDrawable
        }

    private fun getDrawable(state: Int): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        setShapeType(gradientDrawable)
        setGradient(gradientDrawable)
        setSolidColor(gradientDrawable)
        setBorder(gradientDrawable)
        setRadius(gradientDrawable)
        setSize(gradientDrawable)
        setSelectorColor(gradientDrawable, state)
        return gradientDrawable
    }

    fun into(view: View) {
        ViewCompat.setBackground(view, if (useSelector) selectorDrawable else getDrawable(0))
    }
}