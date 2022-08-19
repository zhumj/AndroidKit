package com.zhumj.androidkit.extend

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.zhumj.androidkit.utils.GsonUtil
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * @Author Created by zhumj
 * @Date 2022/8/17 10:32
 * @Description Preferences DataStore 扩展
 */
object PreferencesDataStoreExt {

    private val Context.preferencesStore: DataStore<Preferences> by preferencesDataStore(
        name = "DataStore_Settings"
    )

    /**
     * 获取 Preferences DataStore 保存的数据
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> Context.getStoreValue(key: String, defaultValue: T): T {
        val data = when (defaultValue) {
            is Int -> getInt(preferencesStore, key,  defaultValue)
            is Long -> getLong(preferencesStore, key,  defaultValue)
            is String -> getString(preferencesStore, key,  defaultValue)
            is Boolean -> getBoolean(preferencesStore, key,  defaultValue)
            is Float -> getFloat(preferencesStore, key,  defaultValue)
            is Double -> getDouble(preferencesStore, key,  defaultValue)
            else -> throw IllegalArgumentException("This type cannot be saved to the Data Store")
        }
        return data as T
    }

    /**
     * Preferences DataStore 保存数据
     */
    fun <T> Context.putStoreValue(key: String, value: T) {
        runBlocking {
            when (value) {
                is Int -> putInt(preferencesStore, key, value)
                is Long -> putLong(preferencesStore, key, value)
                is String -> putString(preferencesStore, key, value)
                is Boolean -> putBoolean(preferencesStore, key, value)
                is Float -> putFloat(preferencesStore, key, value)
                is Double -> putDouble(preferencesStore, key, value)
                else -> putString(preferencesStore, key, value.toString())
//                else -> throw IllegalArgumentException("This type cannot be saved to the Data Store")
            }
        }
    }

    /**
     * 获取 Preferences DataStore 保存的限制有效期的数据
     */
    fun <T> Context.getStoreFiniteTimeValue(key: String): StoreFiniteTimeEntity<T>? {
        return GsonUtil.from<StoreFiniteTimeEntity<T>>(
            getString(preferencesStore, key, "")
        )
    }

    /**
     * Preferences DataStore 保存限制有效期的数据
     */
    fun <T> Context.putStoreFiniteTimeValue(key: String, value: T, currentTime: Long = System.currentTimeMillis(), finiteTime: Long = 0) {
        StoreFiniteTimeEntity(currentTime, finiteTime, value).also {
            runBlocking {
                putString(preferencesStore, key, GsonUtil.parse(it))
            }
        }
    }

    /**
     * 清除 Preferences DataStore 保存的所有数据
     */
    fun Context.cleanStore() {
        runBlocking {
            preferencesStore.edit { it.clear() }
        }
    }

    /* *********************************************** get *********************************************** */
    private fun getString(preferencesStore: DataStore<Preferences>, key: String, defaultValue: String) = runBlocking {
        return@runBlocking preferencesStore.data.map {
            it[stringPreferencesKey(key)] ?: defaultValue
        }.first()
    }

    private fun getInt(preferencesStore: DataStore<Preferences>, key: String, defaultValue: Int) = runBlocking {
        return@runBlocking preferencesStore.data.map {
            it[intPreferencesKey(key)] ?: defaultValue
        }.first()
    }

    private fun getLong(preferencesStore: DataStore<Preferences>, key: String, defaultValue: Long) = runBlocking {
        return@runBlocking preferencesStore.data.map {
            it[longPreferencesKey(key)] ?: defaultValue
        }.first()
    }

    private fun getFloat(preferencesStore: DataStore<Preferences>, key: String, defaultValue: Float) = runBlocking {
        return@runBlocking preferencesStore.data.map {
            it[floatPreferencesKey(key)] ?: defaultValue
        }.first()
    }

    private fun getDouble(preferencesStore: DataStore<Preferences>, key: String, defaultValue: Double) = runBlocking {
        return@runBlocking preferencesStore.data.map {
            it[doublePreferencesKey(key)] ?: defaultValue
        }.first()
    }

    private fun getBoolean(preferencesStore: DataStore<Preferences>, key: String, defaultValue: Boolean) = runBlocking {
        return@runBlocking preferencesStore.data.map {
            it[booleanPreferencesKey(key)] ?: defaultValue
        }.first()
    }

    /* *********************************************** put *********************************************** */
    private suspend fun putString(preferencesStore: DataStore<Preferences>, key: String, value: String) {
        preferencesStore.edit { it[stringPreferencesKey(key)] = value }
    }

    private suspend fun putInt(preferencesStore: DataStore<Preferences>, key: String, value: Int) {
        preferencesStore.edit { it[intPreferencesKey(key)] = value }
    }

    private suspend fun putLong(preferencesStore: DataStore<Preferences>, key: String, value: Long) {
        preferencesStore.edit { it[longPreferencesKey(key)] = value }
    }

    private suspend fun putFloat(preferencesStore: DataStore<Preferences>, key: String, value: Float) {
        preferencesStore.edit { it[floatPreferencesKey(key)] = value }
    }

    private suspend fun putDouble(preferencesStore: DataStore<Preferences>, key: String, value: Double) {
        preferencesStore.edit { it[doublePreferencesKey(key)] = value }
    }

    private suspend fun putBoolean(preferencesStore: DataStore<Preferences>, key: String, value: Boolean) {
        preferencesStore.edit { it[booleanPreferencesKey(key)] = value }
    }

}

/**
 * 存储的带有效期的数据实体，计算是否过期：
 * currentTime = System.currentTimeMillis()
 * currentTime - StoreFiniteTimeEntity.time > StoreFiniteTimeEntity.finiteTime: 已过有效期
 * currentTime - StoreFiniteTimeEntity.time <= StoreFiniteTimeEntity.finiteTime: 未过有效期
 */
data class StoreFiniteTimeEntity<T>(
    val time: Long,// 存储时间
    val finiteTime: Long,// 有效时长
    val data: T// 数据
)
