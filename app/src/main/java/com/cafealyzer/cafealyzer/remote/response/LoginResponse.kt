package com.cafealyzer.cafealyzer.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val description: String,
    @SerializedName("status_code") val statusCode: Int,
    val token: String
)