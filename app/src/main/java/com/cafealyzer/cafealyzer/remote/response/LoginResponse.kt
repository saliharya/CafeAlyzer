package com.cafealyzer.cafealyzer.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status_code") val statusCode: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("token") val token: String?
)