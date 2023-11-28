package com.cafealyzer.cafealyzer.remote.response

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    val data: String,
    val description: String,
    @SerializedName("response_type") val responseType: String,
    @SerializedName("status_code") val statusCode: Int
)
