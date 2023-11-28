package com.cafealyzer.cafealyzer.repository

import com.cafealyzer.cafealyzer.remote.request.LoginRequest
import com.cafealyzer.cafealyzer.remote.request.RegistrationRequest
import com.cafealyzer.cafealyzer.remote.response.LoginResponse
import com.cafealyzer.cafealyzer.remote.response.RegistrationResponse
import com.cafealyzer.cafealyzer.remote.service.AuthService
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call
import javax.inject.Inject

@ViewModelScoped
class AuthRepository @Inject constructor(private val authService: AuthService) {
    fun registerUser(registrationRequest: RegistrationRequest): Call<RegistrationResponse> {
        return authService.registerUser(registrationRequest)
    }

    fun loginUser(loginRequest: LoginRequest): Call<LoginResponse> {
        return authService.loginUser(loginRequest)
    }
}
