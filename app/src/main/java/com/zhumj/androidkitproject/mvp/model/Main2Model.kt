package com.zhumj.androidkitproject.mvp.model

import com.zhumj.androidkitproject.mvp.contract.Main2Contract
import kotlinx.coroutines.delay

/**
 * 这里在 MVP 中是真正执行数据请求的地方
 */
class Main2Model: Main2Contract.Model2 {

    override suspend fun queryDates(): List<Int> {
        delay(2000)
        return arrayListOf(0, 1, 2, 3, 4)
    }

}