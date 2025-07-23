package com.compose.base.data.repository

import com.compose.base.data.model.request.LoginRequest
import com.compose.base.data.model.response.AuthResponseDto
import com.compose.base.data.remote.ApiService
import com.compose.base.data.remote.ResultWrapper
import com.compose.base.data.remote.callApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: ApiService,
) : AuthRepository {

    override suspend fun login(request: LoginRequest): Flow<ResultWrapper<AuthResponseDto>> {
        return callApi { authApiService.login(request) }
    }

    override suspend fun logout(): Flow<ResultWrapper<Unit>> = flow {
        // Just example
        emit(ResultWrapper.Success(Unit))
    }
}
