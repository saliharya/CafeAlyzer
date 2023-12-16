package com.cafealyzer.cafealyzer.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("data") val data: List<ReviewData>,
    @SerializedName("response_type") val responseType: String,
    @SerializedName("description") val description: String,
)

data class ReviewData(
    @SerializedName("_id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("cafeUser") val cafeUser: String,
    @SerializedName("cafeCompetitor") val cafeCompetitor: String,
    @SerializedName("date") val date: String,
    @SerializedName("positiveFeedback") val positiveFeedback: List<String>,
    @SerializedName("negativeFeedback") val negativeFeedback: List<String>,
    @SerializedName("positiveFeedbackCompetitor") val positiveFeedbackCompetitor: List<String>,
    @SerializedName("negativeFeedbackCompetitor") val negativeFeedbackCompetitor: List<String>,
    @SerializedName("suggestion") val suggestion: List<String>,
)
