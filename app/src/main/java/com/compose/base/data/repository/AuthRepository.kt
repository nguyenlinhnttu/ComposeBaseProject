package com.compose.base.data.repository

import com.compose.base.data.model.request.LoginRequest
import com.compose.base.data.model.response.AuthResponseDto
import com.compose.base.data.remote.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request: LoginRequest): Flow<ResultWrapper<AuthResponseDto>>
    suspend fun logout(): Flow<ResultWrapper<Unit>> // Example
    // suspend fun register(request: RegisterRequest): ResultWrapper<AuthResponseDto>
}
