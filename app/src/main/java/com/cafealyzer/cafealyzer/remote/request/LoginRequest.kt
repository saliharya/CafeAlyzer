package com.cafealyzer.cafealyzer.remote.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("password") val password: String?,
    @SerializedName("username") val username: String?
)
