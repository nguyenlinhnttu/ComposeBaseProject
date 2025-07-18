package com.compose.base.data.repository

import com.compose.base.data.model.request.LoginRequest
import com.compose.base.data.model.response.AuthResponseDto
import com.compose.base.data.remote.ResultWrapper

interface AuthRepository {
    suspend fun login(request: LoginRequest): ResultWrapper<AuthResponseDto>
    suspend fun logout(): ResultWrapper<Unit> // Example
    // suspend fun register(request: RegisterRequest): ResultWrapper<AuthResponseDto>
}
