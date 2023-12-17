package com.cafealyzer.cafealyzer.remote.service

import com.cafealyzer.cafealyzer.remote.response.CafeDetailResponse
import com.cafealyzer.cafealyzer.remote.response.FindCafeResponse
import com.cafealyzer.cafealyzer.remote.response.NearbyCafeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapService {
    @GET("/maps/nearby-search")
    fun getNearbyCafe(
        @Query("keyword") keyword: String?,
        @Query("location") location: String?,
        @Query("radius") radius: Int,
    ): Call<NearbyCafeResponse>

    @GET("/maps/find-place")
    fun findCafe(
        @Query("input") input: String?,
        @Query("location") location: String?,
        @Query("radius") radius: String?,
    ): Call<FindCafeResponse>

    @GET("/maps/place-detail/{place_id}")
    fun getCafeDetail(
        @Path("place_id") placeId: String,
    ): Call<CafeDetailResponse>
}
