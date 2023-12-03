package com.cafealyzer.cafealyzer.repository

import com.cafealyzer.cafealyzer.remote.response.UserResponse
import com.cafealyzer.cafealyzer.remote.service.UserService
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Call
import javax.inject.Inject

@ViewModelScoped
class UserRepository @Inject constructor(private val userService: UserService) {
    fun getUserData(): Call<UserResponse> {
        return userService.getUser()
    }
}