package com.cafealyzer.cafealyzer.repository

import com.cafealyzer.cafealyzer.remote.request.HistoryRequest
import com.cafealyzer.cafealyzer.remote.response.HistoryResponse
import com.cafealyzer.cafealyzer.remote.service.DeleteHistoryResponse
import com.cafealyzer.cafealyzer.remote.service.HistoryService
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call
import javax.inject.Inject

@ViewModelScoped
class HistoryRepository @Inject constructor(private val historyService: HistoryService) {
    fun postHistory(historyRequest: HistoryRequest): Call<HistoryResponse> {
        return historyService.postHistory(historyRequest)
    }

    fun getHistory(): Call<HistoryResponse> {
        return historyService.getHistory()
    }

    fun deleteHistory(historyId: String): Call<DeleteHistoryResponse> {
        return historyService.deleteHistory(historyId)
    }
}
