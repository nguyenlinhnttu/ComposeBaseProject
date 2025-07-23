package com.compose.base.data.remote

import com.compose.base.data.model.response.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

fun <T> callApi(
    apiCall: suspend () -> Response<T>,
): Flow<ResultWrapper<T>> = flow {
    emit(ResultWrapper.Loading(true)) // show loading

    try {
        val response = apiCall()
        emit(ResultWrapper.Loading(false))
        if (response.isSuccessful) {
            response.body()?.let {
                emit(ResultWrapper.Success(it))
            } ?: emit(ResultWrapper.Error(code = response.code(), error = null))
        } else {
            val errorResponse = response.errorBody()?.string()?.let {
                try {
                    Gson().fromJson(it, ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }
            }
            emit(ResultWrapper.Error(response.code(), errorResponse))
        }
    } catch (throwable: Throwable) {
        emit(ResultWrapper.Loading(false))
        emit(ResultWrapper.Failure(throwable))
    }
}.flowOn(Dispatchers.IO)

/**
* Executes two API calls concurrently and emits a [Flow] of [ResultWrapper] containing a [Pair] of results.
*
* This function is useful when you need to fetch two independent API resources at the same time
* and combine their results once both are completed.
*
* ---
* ## Example Usage:
*
* ### 1. Define your API service:
*
* interface ApiService {
 @GET("endpoint1")
 suspend fun fetchData1(): Response<DataModel1>

 @GET("endpoint2")
 suspend fun fetchData2(): Response<DataModel2>
 }
*
*
* ### 2. Use in repository:
*
* override fun fetchAllData(): Flow<ResultWrapper<Pair<DataModel1, DataModel2>>> {
 return callApiZip(
 apiCall1 = { apiService.fetchData1() },
 apiCall2 = { apiService.fetchData2() }
 )
 }
*
*
* ### 3. Collect in ViewModel:
 viewModelScope.launch {
 dataRepository.fetchAllData().collect { result ->
 when (result) {
 is ResultWrapper.Success -> {
 val (data1, data2) = result.value
 // Handle success
 }
 else -> {
 handleFlowApi(result = result)
 }
 }
 }
 }
*
*/
fun <T1, T2> callApiZip(
    apiCall1: suspend () -> Response<T1>,
    apiCall2: suspend () -> Response<T2>,
): Flow<ResultWrapper<Pair<T1, T2>>> = flow {
    emit(ResultWrapper.Loading(true)) // show loading

    try {
        coroutineScope {
            val deferred1 = async { apiCall1() }
            val deferred2 = async { apiCall2() }

            val response1 = deferred1.await()
            val response2 = deferred2.await()
            emit(ResultWrapper.Loading(false)) // hide loading
            if (response1.isSuccessful && response2.isSuccessful) {
                val body1 = response1.body()
                val body2 = response2.body()

                if (body1 != null && body2 != null) {
                    emit(ResultWrapper.Success(Pair(body1, body2)))
                } else {
                    emit(
                        ResultWrapper.Error(
                            code = response1.code(),
                            error = ErrorResponse(message = "One or both responses returned empty body."),
                        ),
                    )
                }
            } else {
                val error1 = response1.errorBody()?.string()?.let {
                    try {
                        Gson().fromJson(it, ErrorResponse::class.java)
                    } catch (e: Exception) {
                        null
                    }
                }
                val error2 = response2.errorBody()?.string()?.let {
                    try {
                        Gson().fromJson(it, ErrorResponse::class.java)
                    } catch (e: Exception) {
                        null
                    }
                }

                if (!response1.isSuccessful) {
                    emit(ResultWrapper.Error(response1.code(), error1))
                } else {
                    emit(ResultWrapper.Error(response2.code(), error2))
                }
            }
        }
    } catch (t: Throwable) {
        emit(ResultWrapper.Loading(false)) // hide loading
        emit(ResultWrapper.Failure(t))
    }
}.flowOn(Dispatchers.IO)
