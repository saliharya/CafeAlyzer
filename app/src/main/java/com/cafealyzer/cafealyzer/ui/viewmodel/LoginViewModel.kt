package com.cafealyzer.cafealyzer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafealyzer.cafealyzer.local.DataStoreManager
import com.cafealyzer.cafealyzer.remote.request.LoginRequest
import com.cafealyzer.cafealyzer.remote.response.LoginResponse
import com.cafealyzer.cafealyzer.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository, private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loginUser(username: String, password: String): LiveData<LoginResponse> {
        _isLoading.value = true
        val loginRequest = LoginRequest(password, username)
        val liveDataResponse = MutableLiveData<LoginResponse>()
        authRepository.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                _loginSuccess.value = response.isSuccessful
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    token.let {
                        viewModelScope.launch {
                            dataStoreManager.saveToken(token.toString())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _loginSuccess.postValue(false)
            }
        })
        return liveDataResponse
    }
}
