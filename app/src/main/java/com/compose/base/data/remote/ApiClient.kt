package com.compose.base.data.remote

import com.compose.base.data.model.response.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> callApi(
    apiCall: suspend () -> Response<T>,
): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        try {
            ResultWrapper.Loading(true)
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                ResultWrapper.Loading(false)
                response.body()?.let {
                    ResultWrapper.Success(it)
                } ?: ResultWrapper.Error(code = response.code(), error = null)
            } else {
                val errorResponse = response.errorBody()?.string()?.let {
                    try {
                        Gson().fromJson(it, ErrorResponse::class.java)
                    } catch (e: Exception) {
                        null
                    }
                }
                ResultWrapper.Loading(false)
                ResultWrapper.Error(response.code(), errorResponse)
            }
        } catch (throwable: Throwable) {
            ResultWrapper.Loading(false)
            ResultWrapper.Failure(throwable)
        }
    }
}

suspend fun <T1, T2> callApiZip(
    apiCall1: suspend () -> Response<T1>,
    apiCall2: suspend () -> Response<T2>,
): ResultWrapper<Pair<T1, T2>> {
    return withContext(Dispatchers.IO) {
        try {
            ResultWrapper.Loading(true)
            val deferredResponse1 = async { apiCall1.invoke() }
            val deferredResponse2 = async { apiCall2.invoke() }

            val response1 = deferredResponse1.await()
            val response2 = deferredResponse2.await()

            if (response1.isSuccessful && response2.isSuccessful) {
                val body1 = response1.body()
                val body2 = response2.body()
                if (body1 != null && body2 != null) {
                    ResultWrapper.Loading(false)
                    ResultWrapper.Success(Pair(body1, body2))
                } else {
                    ResultWrapper.Loading(false)
                    ResultWrapper.Error(
                        code = response1.code(),
                        error = ErrorResponse(message = "One or both responses returned empty body."),
                    )
                }
            } else {
                val errorBody1 = response1.errorBody()?.string()
                val errorBody2 = response2.errorBody()?.string()

                val errorResponse1 = errorBody1?.let {
                    try {
                        Gson().fromJson(it, ErrorResponse::class.java)
                    } catch (e: Exception) {
                        null
                    }
                }
                val errorResponse2 = errorBody2?.let {
                    try {
                        Gson().fromJson(it, ErrorResponse::class.java)
                    } catch (e: Exception) {
                        null
                    }
                }
                ResultWrapper.Loading(false)
                if (!response1.isSuccessful) {
                    ResultWrapper.Error(response1.code(), errorResponse1)
                } else {
                    ResultWrapper.Error(response2.code(), errorResponse2)
                }
            }
        } catch (throwable: Throwable) {
            ResultWrapper.Loading(false)
            ResultWrapper.Failure(throwable)
        }
    }
}
