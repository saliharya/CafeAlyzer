package com.cafealyzer.cafealyzer.remote.service
import com.cafealyzer.cafealyzer.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("/users/me")
    fun getUser(): Call<UserResponse>
}