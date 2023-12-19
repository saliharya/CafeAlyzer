package com.cafealyzer.cafealyzer.remote.service

import com.cafealyzer.cafealyzer.remote.request.HistoryRequest
import com.cafealyzer.cafealyzer.remote.response.HistoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HistoryService {
    @POST("/histories/")
    fun postHistory(@Body historyRequest: HistoryRequest): Call<HistoryResponse>

    @GET("/histories/me")
    fun getHistory(): Call<HistoryResponse>

    @DELETE("/histories/{id}")
    fun deleteHistory(@Path("id") id: String): Call<DeleteHistoryResponse>

}