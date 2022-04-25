package com.zhumj.androidkitproject.mvp.contract

/**
 * 这里是 MVP 定义接口的地方
 */
interface MainContract {
    interface Model {
        fun queryDates(): List<Int>
    }

    interface View {
        fun queryDatesSuccess(dates: List<Int>)
        fun queryDatesFailure(errCode: Int, errMsg: String)
    }

    interface Presenter {
        fun queryDates()
    }
}