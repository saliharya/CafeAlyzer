package com.cafealyzer.cafealyzer.model

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("type") val type: String?,
    @SerializedName("access_token") val accessToken: String?
)