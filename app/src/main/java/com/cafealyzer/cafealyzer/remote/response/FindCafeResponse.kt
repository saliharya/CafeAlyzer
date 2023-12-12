package com.cafealyzer.cafealyzer.remote.response

import com.google.gson.annotations.SerializedName

data class FindCafeResponse(
    @SerializedName("candidates") val candidates: List<FindCafeResult>,
    @SerializedName("status") val status: String,
)

data class FindCafeResult(
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("geometry") val findCafeGeometry: FindCafeGeometry,
    @SerializedName("place_id") val placeId: String,
)

data class FindCafeGeometry(
    @SerializedName("location") val location: Location,
)

data class Location(
    @SerializedName("lng") val lng: Double,
    @SerializedName("lat") val lat: Double,
)
