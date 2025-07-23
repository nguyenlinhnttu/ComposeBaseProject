package com.compose.base.data.remote

import com.compose.base.data.model.request.LoginRequest
import com.compose.base.data.model.response.AuthResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("v1/users/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponseDto>

    // @POST("auth/register")
    // suspend fun register(@Body request: RegisterRequest): Response<AuthResponseDto>
}
