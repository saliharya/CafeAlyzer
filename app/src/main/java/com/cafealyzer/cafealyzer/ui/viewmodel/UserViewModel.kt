package com.cafealyzer.cafealyzer.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cafealyzer.cafealyzer.local.DataStoreManager
import com.cafealyzer.cafealyzer.model.UserData
import com.cafealyzer.cafealyzer.remote.response.UserResponse
import com.cafealyzer.cafealyzer.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {
    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> get() = _userData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchUserData() : LiveData<UserResponse> {
        _isLoading.value = true
        val liveDataResponse = MutableLiveData<UserResponse>()
        userRepository.getUserData().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userData.value = response.body()?.data
                    Log.d("UserViewModel", "User data retrieved successfully ${_userData.value}")
                } else {
                    Log.e("UserViewModel", "Unsuccessful response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("UserViewModel", "Network request failure: ${t.message}")
            }
        })
        return liveDataResponse
    }

    fun isUserAuthenticated(): Boolean {
        val token = runBlocking { dataStoreManager.getToken() }
        return !token.isNullOrEmpty()
    }

    fun logoutUser() {
        runBlocking { dataStoreManager.clearToken() }
    }
}