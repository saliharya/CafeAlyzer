package com.cafealyzer.cafealyzer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cafealyzer.cafealyzer.remote.request.RegistrationRequest
import com.cafealyzer.cafealyzer.remote.response.RegistrationResponse
import com.cafealyzer.cafealyzer.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean>
        get() = _registerSuccess

    fun registerUser(
        email: String, username: String, password: String
    ): LiveData<RegistrationResponse> {
        val registerRequest = RegistrationRequest(email, password, username)
        val liveDataResponse = MutableLiveData<RegistrationResponse>()
        authRepository.registerUser(registerRequest)
            .enqueue(object : Callback<RegistrationResponse> {

                override fun onResponse(
                    call: Call<RegistrationResponse>, response: Response<RegistrationResponse>
                ) {
                    _registerSuccess.value = response.isSuccessful
                }

                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    _registerSuccess.postValue(false)

                }
            })
        return liveDataResponse
    }
}