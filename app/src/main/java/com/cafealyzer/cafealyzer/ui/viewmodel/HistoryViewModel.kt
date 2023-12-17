package com.cafealyzer.cafealyzer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cafealyzer.cafealyzer.remote.request.HistoryRequest
import com.cafealyzer.cafealyzer.remote.response.HistoryResponse
import com.cafealyzer.cafealyzer.remote.response.ReviewData
import com.cafealyzer.cafealyzer.remote.service.DeleteHistoryResponse
import com.cafealyzer.cafealyzer.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyRepository: HistoryRepository) :
    ViewModel() {
    private val _historySuccess = MutableLiveData<Boolean>()
    val historySuccess: LiveData<Boolean> get() = _historySuccess

    private val _historyData = MutableLiveData<List<ReviewData>>()
    val historyData: LiveData<List<ReviewData>> get() = _historyData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun postHistory(
        cafeAnda: String,
        cafeKompetitor: String,
        positiveFeedbackAnda: List<String>,
        negativeFeedbackAnda: List<String>,
        positiveFeedbackKompetitor: List<String>,
        negativeFeedbackKompetitor: List<String>,
        suggestionAnda: List<String>,
    ): LiveData<HistoryResponse> {
        _isLoading.postValue(true)
        val historyRequest =
            HistoryRequest(
                cafeAnda,
                cafeKompetitor,
                positiveFeedbackAnda,
                negativeFeedbackAnda,
                positiveFeedbackKompetitor,
                negativeFeedbackKompetitor,
                suggestionAnda
            )
        val liveDataResponse = MutableLiveData<HistoryResponse>()
        historyRepository.postHistory(historyRequest)
            .enqueue(object : Callback<HistoryResponse> {

                override fun onResponse(
                    call: Call<HistoryResponse>, response: Response<HistoryResponse>,
                ) {
                    _isLoading.postValue(false)
                    _historySuccess.value = response.isSuccessful
                    Log.d("HistoryViewModel", "Berhasil menyimpan review")
                }

                override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                    _historySuccess.postValue(false)
                    _isLoading.postValue(false)
                }
            })
        return liveDataResponse
    }

    fun getHistory(): LiveData<HistoryResponse> {
        _isLoading.value = true
        val liveDataResponse = MutableLiveData<HistoryResponse>()
        historyRepository.getHistory()
            .enqueue(object : Callback<HistoryResponse> {
                override fun onResponse(
                    call: Call<HistoryResponse>, response: Response<HistoryResponse>,
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _historyData.value = response.body()?.data
                        Log.d(
                            "HistoryViewModel",
                            "History data retrieved successfully ${_historyData.value}"
                        )
                    } else {
                        Log.e("HistoryViewModel", "Unsuccessful response: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("HistoryViewModel", "Network request failure: ${t.message}")
                }
            })
        return liveDataResponse
    }

    fun deleteHistory(historyId: String): LiveData<Boolean> {
        _isLoading.value = true
        val liveDataResponse = MutableLiveData<Boolean>()
        historyRepository.deleteHistory(historyId)
            .enqueue(object : Callback<DeleteHistoryResponse> {

                override fun onResponse(
                    call: Call<DeleteHistoryResponse>,
                    response: Response<DeleteHistoryResponse>
                ) {
                    _isLoading.value = false
                    _historySuccess.value = response.isSuccessful
                    liveDataResponse.value = response.isSuccessful
                    Log.d("HistoryViewModel", "History deleted successfully")
                }

                override fun onFailure(call: Call<DeleteHistoryResponse>, t: Throwable) {
                    _historySuccess.value = false
                    _isLoading.value = false
                    liveDataResponse.value = false
                    Log.e("HistoryViewModel", "Network request failure: ${t.message}")
                }
            })
        return liveDataResponse
    }

}
