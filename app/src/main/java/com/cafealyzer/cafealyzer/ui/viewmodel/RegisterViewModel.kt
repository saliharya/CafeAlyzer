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
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    fun registerUser(username: String, email: String, password: String): LiveData<RegistrationResponse> {
        val registrationRequest = RegistrationRequest(email, password, username)
        val liveDataResponse = MutableLiveData<RegistrationResponse>()
        authRepository.registerUser(registrationRequest).enqueue(object :
            Callback<RegistrationResponse> {
            override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { liveDataResponse.postValue(it) }
                }
            }

            override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                // Handle failure
            }
        })
        return liveDataResponse
    }
}
