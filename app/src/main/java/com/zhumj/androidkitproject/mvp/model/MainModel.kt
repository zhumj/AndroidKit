package com.zhumj.androidkitproject.mvp.model

import com.zhumj.androidkitproject.mvp.contract.MainContract

/**
 * 这里在 MVP 中是真正执行数据请求的地方
 */
class MainModel: MainContract.Model {

    override fun queryDates(): List<Int> {
        return arrayListOf(0, 1, 2, 3, 4)
    }

}