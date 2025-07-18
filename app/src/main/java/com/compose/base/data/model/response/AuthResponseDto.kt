package com.compose.base.data.model.response

import com.compose.base.data.model.UserDto
import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("user") val user: UserDto?, // Link to a common UserDto
)
