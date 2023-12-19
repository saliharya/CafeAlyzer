package com.cafealyzer.cafealyzer.remote.service

import com.google.gson.annotations.SerializedName

class DeleteHistoryResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("description") val description: String,
)
