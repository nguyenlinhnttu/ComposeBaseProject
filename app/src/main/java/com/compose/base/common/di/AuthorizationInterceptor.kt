package com.compose.base.common.di

import com.compose.base.BuildConfig
import com.compose.base.common.helper.LocalDataManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val localData: LocalDataManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            localData.getAccessTokenImmediate()
        }
        val newRequest = chain.request().newBuilder().apply {
            if (!token.isNullOrEmpty()) {
                header("Authorization", "Bearer $token")
            }
            header("app-version", BuildConfig.VERSION_NAME)
            header("Accept", "application/json")
        }.build()

        return chain.proceed(newRequest)
    }
}
