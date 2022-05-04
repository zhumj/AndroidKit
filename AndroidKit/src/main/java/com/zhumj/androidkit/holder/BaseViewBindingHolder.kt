package com.zhumj.androidkit.holder

import androidx.viewbinding.ViewBinding

/**
 * @author Created by zhumj
 * @date 2022/5/4 13:45
 * @description : ViewHolder 基类，方便 ViewBinding 使用
 */
open class BaseViewBindingHolder<VB : ViewBinding>(val vb: VB): BaseViewHolder(vb.root)