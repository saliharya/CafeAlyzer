package com.cafealyzer.cafealyzer.remote.request

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("email") val email: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("username") val username: String?
)