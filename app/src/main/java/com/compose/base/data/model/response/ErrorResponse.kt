package com.compose.base.data.model.response

import com.google.gson.annotations.SerializedName

// This is an example of a common model if your API returns a consistent error structure
data class ErrorResponse(
    @SerializedName("statusCode")
    val statusCode: Int? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("error")
    val error: String? = null,
)
