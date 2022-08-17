package com.zhumj.androidkit.extend

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * @Author Created by zhumj
 * @Date 2022/8/17 10:32
 * @Description Preferences DataStore 扩展
 */
object PreferencesDataStoreExt {

    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "DataStore_Settings"
    )

    @Suppress("UNCHECKED_CAST")
    fun <T> Context.getPreferencesDataStoreValue(key: String, defaultValue: T): T {
        val data = when (defaultValue) {
            is Int -> runBlocking {
                return@runBlocking preferencesDataStore.data.map {
                    it[intPreferencesKey(key)] ?: defaultValue
                }.first()
            }
            is Long -> runBlocking {
                return@runBlocking preferencesDataStore.data.map {
                    it[longPreferencesKey(key)] ?: defaultValue
                }.first()
            }
            is String -> runBlocking {
                return@runBlocking preferencesDataStore.data.map {
                    it[stringPreferencesKey(key)] ?: defaultValue
                }.first()
            }
            is Boolean -> runBlocking {
                return@runBlocking preferencesDataStore.data.map {
                    it[booleanPreferencesKey(key)] ?: defaultValue
                }.first()
            }
            is Float -> runBlocking {
                return@runBlocking preferencesDataStore.data.map {
                    it[floatPreferencesKey(key)] ?: defaultValue
                }.first()
            }
            is Double -> runBlocking {
                return@runBlocking preferencesDataStore.data.map {
                    it[doublePreferencesKey(key)] ?: defaultValue
                }.first()
            }
            else -> throw IllegalArgumentException("This type cannot be saved to the Data Store")
        }
        return data as T
    }

    fun <T> Context.putPreferencesDataStoreValue(key: String, value: T) {
        runBlocking {
            when (value) {
                is Int -> putInt(preferencesDataStore, key, value)
                is Long -> putLong(preferencesDataStore, key, value)
                is String -> putString(preferencesDataStore, key, value)
                is Boolean -> putBoolean(preferencesDataStore, key, value)
                is Float -> putFloat(preferencesDataStore, key, value)
                is Double -> putDouble(preferencesDataStore, key, value)
                else -> putString(preferencesDataStore, key, value.toString())
//                else -> throw IllegalArgumentException("This type cannot be saved to the Data Store")
            }
        }
    }

    fun Context.cleanPreferencesDataStore() {
        runBlocking {
            preferencesDataStore.edit { it.clear() }
        }
    }

    private suspend fun putString(preferencesDataStore: DataStore<Preferences>, key: String, value: String) {
        preferencesDataStore.edit { it[stringPreferencesKey(key)] = value }
    }

    private suspend fun putInt(preferencesDataStore: DataStore<Preferences>, key: String, value: Int) {
        preferencesDataStore.edit { it[intPreferencesKey(key)] = value }
    }

    private suspend fun putLong(preferencesDataStore: DataStore<Preferences>, key: String, value: Long) {
        preferencesDataStore.edit { it[longPreferencesKey(key)] = value }
    }

    private suspend fun putFloat(preferencesDataStore: DataStore<Preferences>, key: String, value: Float) {
        preferencesDataStore.edit { it[floatPreferencesKey(key)] = value }
    }

    private suspend fun putDouble(preferencesDataStore: DataStore<Preferences>, key: String, value: Double) {
        preferencesDataStore.edit { it[doublePreferencesKey(key)] = value }
    }

    private suspend fun putBoolean(preferencesDataStore: DataStore<Preferences>, key: String, value: Boolean) {
        preferencesDataStore.edit { it[booleanPreferencesKey(key)] = value }
    }

}