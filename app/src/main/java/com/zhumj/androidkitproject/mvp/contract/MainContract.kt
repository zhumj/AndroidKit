package com.zhumj.androidkitproject.mvp.contract

import androidx.lifecycle.LifecycleCoroutineScope

/**
 * 这里是 MVP 定义接口的地方
 */
interface MainContract {
    interface Model {
        suspend fun queryDates(): List<Int>
    }

    interface View {
        fun queryDatesSuccess(dates: List<Int>)
        fun queryDatesFailure(errCode: Int, errMsg: String)
    }

    interface Presenter {
        fun queryDates(lifecycleScope: LifecycleCoroutineScope)
    }
}