package com.compose.base.data.repository

import com.compose.base.data.model.request.LoginRequest
import com.compose.base.data.model.response.AuthResponseDto
import com.compose.base.data.remote.AuthApiService
import com.compose.base.data.remote.ResultWrapper
import com.compose.base.data.remote.callApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // Make this a singleton for the app's lifetime
class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthRepository {

    override suspend fun login(request: LoginRequest): ResultWrapper<AuthResponseDto> {
        return callApi { authApiService.login(request) }
    }

    override suspend fun logout(): ResultWrapper<Unit> {
        // Implement logout logic (e.g., clear tokens from SharedPreferences)
        // For now, just return success
        return ResultWrapper.Success(Unit)
    }
}
