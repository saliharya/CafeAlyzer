package com.cafealyzer.cafealyzer.remote.service

import com.cafealyzer.cafealyzer.remote.request.LoginRequest
import com.cafealyzer.cafealyzer.remote.request.RegistrationRequest
import com.cafealyzer.cafealyzer.remote.response.LoginResponse
import com.cafealyzer.cafealyzer.remote.response.RegistrationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/register")
    fun registerUser(@Body registrationRequest: RegistrationRequest): Call<RegistrationResponse>

    @POST("/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
}