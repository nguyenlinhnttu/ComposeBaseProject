package com.compose.base.common.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

@Singleton
class DataStoreClient @Inject constructor(private val context: Context) {

    private val dataStore = context.dataStore

    suspend fun putString(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    fun getString(key: String, default: String? = null) =
        dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: default
        }

    suspend fun putBoolean(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    fun getBoolean(key: String, default: Boolean = false) =
        dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: default
        }

    suspend fun putInt(key: String, value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    fun getInt(key: String, default: Int? = null) = dataStore.data.map { preferences ->
        preferences[intPreferencesKey(key)] ?: default
    }

    suspend fun putLong(key: String, value: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(key)] = value
        }
    }

    fun getLong(key: String, default: Long? = null) = dataStore.data.map { preferences ->
        preferences[longPreferencesKey(key)] ?: default
    }

    suspend fun putFloat(key: String, value: Float) {
        dataStore.edit { preferences ->
            preferences[floatPreferencesKey(key)] = value
        }
    }

    suspend fun getFloat(key: String, default: Float? = null) = dataStore.data.map { preferences ->
        preferences[floatPreferencesKey(key)] ?: default
    }

    suspend fun putDouble(key: String, value: Double) {
        dataStore.edit { preferences ->
            preferences[doublePreferencesKey(key)] = value
        }
    }

    suspend fun getDouble(key: String, default: Double? = null) =
        dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)] ?: default
        }

    suspend fun clear(key: String, type: Any) {
        dataStore.edit { preferences ->
            when (type) {
                is String -> preferences.remove(stringPreferencesKey(key))
                is Int -> preferences.remove(intPreferencesKey(key))
                is Boolean -> preferences.remove(booleanPreferencesKey(key))
                is Float -> preferences.remove(floatPreferencesKey(key))
                is Long -> preferences.remove(longPreferencesKey(key))
                is Double -> preferences.remove(doublePreferencesKey(key))
            }
        }
    }

    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
