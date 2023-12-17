package com.cafealyzer.cafealyzer.repository

import com.cafealyzer.cafealyzer.remote.response.CafeDetailResponse
import com.cafealyzer.cafealyzer.remote.response.FindCafeResponse
import com.cafealyzer.cafealyzer.remote.response.NearbyCafeResponse
import com.cafealyzer.cafealyzer.remote.service.MapService
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call
import javax.inject.Inject

@ViewModelScoped
class MapRepository @Inject constructor(private val mapService: MapService) {
    fun getNearbyCafe(keyword: String?, location: String?, radius: Int): Call<NearbyCafeResponse> {
        return mapService.getNearbyCafe(keyword, location, radius)
    }

    fun findCafe(input: String?, location: String?, radius: String?): Call<FindCafeResponse> {
        return mapService.findCafe(input, location, radius)
    }

    fun getCafeDetail(placeId: String): Call<CafeDetailResponse> {
        return mapService.getCafeDetail(placeId)
    }
}