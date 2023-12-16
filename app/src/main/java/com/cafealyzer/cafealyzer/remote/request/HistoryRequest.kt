package com.cafealyzer.cafealyzer.remote.request

import com.google.gson.annotations.SerializedName

data class HistoryRequest(
    @SerializedName("cafeCompetitor") val cafeCompetitor: String?,
    @SerializedName("cafeUser") val cafeUser: String?,
    @SerializedName("negativeFeedback") val negativeFeedback: List<String>?,
    @SerializedName("negativeFeedbackCompetitor") val negativeFeedbackCompetitor: List<String>?,
    @SerializedName("positiveFeedback") val positiveFeedback: List<String>?,
    @SerializedName("positiveFeedbackCompetitor") val positiveFeedbackCompetitor: List<String>?,
    @SerializedName("suggestion") val suggestion: List<String>?
)