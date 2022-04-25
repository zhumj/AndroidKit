package com.zhumj.androidkitproject.mvp.contract

interface MainContract {
    interface Model {
        fun queryDatesDates(): List<String>
    }

    interface View {
        fun queryDatesSuccess(dates: List<String>)
        fun queryDatesFailure(errCode: Int, errMsg: String)
    }

    interface Presenter {
        fun queryDates()
    }
}