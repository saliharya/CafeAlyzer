package com.cafealyzer.cafealyzer.remote.response

import com.cafealyzer.cafealyzer.model.UserData
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("status_code") val statusCode: Int?,
    @SerializedName("response_type") val responseType: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("data") val data: UserData?
)
