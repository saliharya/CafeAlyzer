package com.cafealyzer.cafealyzer.remote.response

import com.google.gson.annotations.SerializedName

data class CafeDetailResponse(
    @SerializedName("result") val result: CafeDetailResult,
    @SerializedName("html_attributions") val htmlAttributions: List<Any>,
    @SerializedName("status") val status: String,
)

data class CafeDetailResult(
    @SerializedName("user_ratings_total") val userRatingsTotal: Int,
    @SerializedName("reviews") val reviews: List<CafeReviewsItem>,
    @SerializedName("rating") val rating: Any,
    @SerializedName("photos") val photos: List<CafeDetailPhotoItem>,
    @SerializedName("place_id") val placeId: String,
)

data class CafeDetailPhotoItem(
    @SerializedName("photo_reference") val photoReference: String,
    @SerializedName("width") val width: Int,
    @SerializedName("html_attributions") val htmlAttributions: List<String>,
    @SerializedName("height") val height: Int,
)

data class CafeReviewsItem(
    @SerializedName("author_name") val authorName: String,
    @SerializedName("profile_photo_url") val profilePhotoUrl: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("author_url") val authorUrl: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("language") val language: String,
    @SerializedName("text") val text: String,
    @SerializedName("time") val time: Int,
    @SerializedName("translated") val translated: Boolean,
    @SerializedName("relative_time_description") val relativeTimeDescription: String,
)