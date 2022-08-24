package com.zhumj.androidkitproject.mvp.contract

import androidx.lifecycle.LifecycleCoroutineScope

/**
 * 这里是 MVP 定义接口的地方
 */
interface Main2Contract {
    interface Model2 {
        suspend fun queryDates(): List<Int>
    }

    interface View2 {

    }

    interface Presenter2 {
        fun queryDates(lifecycleScope: LifecycleCoroutineScope)
    }
}