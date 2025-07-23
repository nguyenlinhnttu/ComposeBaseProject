package com.compose.base.common.helper

import com.compose.base.common.utils.AESEncryptionUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataManager @Inject constructor(private val dataStore: DataStoreClient) {

    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val IS_LOGIN = "is_login"
    }

    suspend fun saveAccessToken(accessToken: String) {
        val encrypted = AESEncryptionUtil.encrypt(accessToken)
        encrypted?.let {
            dataStore.putString(ACCESS_TOKEN, encrypted)
        }
    }

    fun getAccessToken(): Flow<String?> {
        return dataStore.getString(ACCESS_TOKEN, null).map { encryptedToken ->
            encryptedToken?.let {
                try {
                    AESEncryptionUtil.decrypt(it)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    // You might also want an immediate (one-shot) getter for scenarios that don't need reactivity
    suspend fun getAccessTokenImmediate(): String? {
        val encrypted = dataStore.getString(ACCESS_TOKEN, null).first()
        return encrypted?.let {
            try {
                AESEncryptionUtil.decrypt(it)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun setIsLogin(isLogin: Boolean) {
        dataStore.putBoolean(IS_LOGIN, isLogin)
    }

    fun getLoginStatus(): Flow<Boolean> {
        return dataStore.getBoolean(IS_LOGIN, false)
    }

    suspend fun getLoginStatusImmediate(): Boolean {
        return dataStore.getBoolean(IS_LOGIN, false).first()
    }
}
