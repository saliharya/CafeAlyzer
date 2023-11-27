package com.cafealyzer.cafealyzer.repository

import com.cafealyzer.cafealyzer.remote.response.LoginResponse
import com.cafealyzer.cafealyzer.remote.service.AuthService
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class AuthRepository @Inject constructor(private val authService: AuthService) {
    suspend fun login(username: String, password: String): Response<LoginResponse> {
        return authService.login(
            "password", username, password, "your_scope", "your_client_id", "your_client_secret"
        )
    }
}