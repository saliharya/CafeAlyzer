package com.cafealyzer.cafealyzer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cafealyzer.cafealyzer.remote.response.CafeDetailResponse
import com.cafealyzer.cafealyzer.remote.response.FindCafeResponse
import com.cafealyzer.cafealyzer.remote.response.FindCafeResult
import com.cafealyzer.cafealyzer.remote.response.NearbyCafeResponse
import com.cafealyzer.cafealyzer.remote.response.NearbyCafeResult
import com.cafealyzer.cafealyzer.repository.MapRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val mapRepository: MapRepository,
) : ViewModel() {
    var deviceLatLng: LatLng = LatLng(0.0, 0.0)
    var latitude = deviceLatLng.latitude
    var longitude = deviceLatLng.longitude

    private val _nearbyCafeData = MutableLiveData<List<NearbyCafeResult>>()
    val nearbyCafeData: LiveData<List<NearbyCafeResult>> get() = _nearbyCafeData

    private val _findCafeData = MutableLiveData<List<FindCafeResult>>()
    val findCafeData: LiveData<List<FindCafeResult>> get() = _findCafeData

    private val _selectedCafe = MutableLiveData<FindCafeResult>()
    val selectedCafe: LiveData<FindCafeResult> get() = _selectedCafe

    private val _cafeDetail = MutableLiveData<CafeDetailResponse>()
    val cafeDetail: LiveData<CafeDetailResponse> get() = _cafeDetail

    private val _cafeDetail2 = MutableLiveData<CafeDetailResponse>()
    val cafeDetail2: LiveData<CafeDetailResponse> get() = _cafeDetail2

    private val _cafeList = MutableLiveData<List<CafeData>>(emptyList())
    val cafeList: LiveData<List<CafeData>> get() = _cafeList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading


    fun fetchNearbyCafeData(): LiveData<NearbyCafeResponse> {
        val keyword = "cafe"
        val location = "$latitude,$longitude"
        val radius = 500

        _isLoading.value = true
        val liveDataResponse = MutableLiveData<NearbyCafeResponse>()
        mapRepository.getNearbyCafe(keyword, location, radius)
            .enqueue(object : Callback<NearbyCafeResponse> {
                override fun onResponse(
                    call: Call<NearbyCafeResponse>,
                    response: Response<NearbyCafeResponse>,
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _nearbyCafeData.value = response.body()?.results
                        Log.d(
                            "MapsViewModel",
                            "Nearby cafe data retrieved successfully ${_nearbyCafeData.value}"
                        )
                    } else {
                        Log.e("MapsViewModel", "Unsuccessful response: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<NearbyCafeResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("MapsViewModel", "Network request failure: ${t.message}")
                }
            })
        return liveDataResponse
    }

    fun findCafe(input: String?): LiveData<FindCafeResponse> {
        val location = "$latitude,$longitude"
        val radius = "500"

        _isLoading.value = true
        val liveDataResponse = MutableLiveData<FindCafeResponse>()
        mapRepository.findCafe(input, location, radius)
            .enqueue(object : Callback<FindCafeResponse> {
                override fun onResponse(
                    call: Call<FindCafeResponse>,
                    response: Response<FindCafeResponse>,
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _findCafeData.value = response.body()?.candidates
                        Log.d(
                            "MapsViewModel",
                            "Cafe data retrieved successfully ${_findCafeData.value}"
                        )
                    } else {
                        Log.e("MapsViewModel", "Unsuccessful response: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<FindCafeResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("MapsViewModel", "Network request failure: ${t.message}")
                }
            })
        return liveDataResponse
    }

    fun getCafeDetail(placeId: String): LiveData<CafeDetailResponse> {
        _isLoading.value = true
        val liveDataResponse = MutableLiveData<CafeDetailResponse>()
        mapRepository.getCafeDetail(placeId)
            .enqueue(object : Callback<CafeDetailResponse> {
                override fun onResponse(
                    call: Call<CafeDetailResponse>,
                    response: Response<CafeDetailResponse>,
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _cafeDetail.value = response.body()
                        Log.d(
                            "MapsViewModel",
                            "Cafe Detail retrieved successfully ${_cafeDetail.value?.result?.reviews}"
                        )
                    } else {
                        Log.e("MapsViewModel", "Unsuccessful response: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<CafeDetailResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("MapsViewModel", "Network request failure: ${t.message}")
                }
            })
        return liveDataResponse
    }

    fun getCafeDetail2(placeId2: String): LiveData<CafeDetailResponse> {
        _isLoading.value = true
        val liveDataResponse = MutableLiveData<CafeDetailResponse>()
        mapRepository.getCafeDetail(placeId2)
            .enqueue(object : Callback<CafeDetailResponse> {
                override fun onResponse(
                    call: Call<CafeDetailResponse>,
                    response: Response<CafeDetailResponse>,
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _cafeDetail2.value = response.body()
                        Log.d(
                            "MapsViewModel",
                            "Cafe Detail retrieved successfully ${_cafeDetail2.value?.result?.reviews}"
                        )
                    } else {
                        Log.e("MapsViewModel", "Unsuccessful response: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<CafeDetailResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("MapsViewModel", "Network request failure: ${t.message}")
                }
            })
        return liveDataResponse
    }

    fun setSelectedCafe(cafe: FindCafeResult) {
        _selectedCafe.value = cafe
    }

    fun reverseCafeList() {
        val temp = _cafeList.value?.reversed()
        _cafeList.value = temp
    }

    fun clearCafeList() {
        _cafeList.value = emptyList()
    }

    fun addCafeToList(title: String, placeId: String) {
        val newCafe = CafeData(title, placeId)
        _cafeList.value = (_cafeList.value ?: emptyList()) + newCafe
    }

    data class CafeData(
        val title: String,
        val placeId: String,
    )
}