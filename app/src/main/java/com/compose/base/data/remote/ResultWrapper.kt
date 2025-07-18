package com.compose.base.data.remote

import com.compose.base.data.model.response.ErrorResponse

sealed class ResultWrapper<out T> {
    data class Loading(val isLoading: Boolean) : ResultWrapper<Nothing>()
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val code: Int? = null, val error: ErrorResponse? = null) :
        ResultWrapper<Nothing>()
    data class Failure(val throwable: Throwable) : ResultWrapper<Nothing>()
}
