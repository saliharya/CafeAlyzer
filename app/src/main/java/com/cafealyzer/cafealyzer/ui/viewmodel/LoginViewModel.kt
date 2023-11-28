package com.cafealyzer.cafealyzer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cafealyzer.cafealyzer.remote.request.LoginRequest
import com.cafealyzer.cafealyzer.remote.response.LoginResponse
import com.cafealyzer.cafealyzer.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    fun loginUser(username: String, password: String): LiveData<LoginResponse> {
        val loginRequest = LoginRequest(password, username)
        val liveDataResponse = MutableLiveData<LoginResponse>()
        authRepository.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    _loginSuccess.postValue(true)
                } else {
                    _loginSuccess.postValue(false)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginSuccess.postValue(false)
            }
        })
        return liveDataResponse
    }
}
