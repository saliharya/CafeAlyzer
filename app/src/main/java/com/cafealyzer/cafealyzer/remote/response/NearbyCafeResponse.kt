package com.cafealyzer.cafealyzer.remote.response

import com.google.gson.annotations.SerializedName

data class NearbyCafeResponse(
    @SerializedName("next_page_token") val nextPageToken: String,
    @SerializedName("html_attributions") val htmlAttributions: List<Any>,
    @SerializedName("results") val results: List<NearbyCafeResult>,
    @SerializedName("status") val status: String,
)

data class NearbyCafeResult(
    @SerializedName("geometry") val nearbyCafeGeometry: NearbyCafeGeometry,
    @SerializedName("place_id") val placeId: String,
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: Double,
)

data class NearbyCafeGeometry(
    @SerializedName("location") val location: NearbyCafeLocation,
)

data class NearbyCafeLocation(
    @SerializedName("lng") val lng : Double,
    @SerializedName("lat") val lat : Double,
)