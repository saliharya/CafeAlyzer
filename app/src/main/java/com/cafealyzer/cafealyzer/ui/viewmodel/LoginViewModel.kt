package com.cafealyzer.cafealyzer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.cafealyzer.cafealyzer.local.DataStoreManager
import com.cafealyzer.cafealyzer.remote.response.LoginResponse
import com.cafealyzer.cafealyzer.repository.AuthRepository
import com.cafealyzer.cafealyzer.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository, private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse?> get() = _loginResponse

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    private val _isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    fun login(email: String, password: String, navController: NavHostController) {
        viewModelScope.launch {
            navController.navigate(Screen.Home.route)

            try {
                val response = authRepository.login(password, email)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.accessToken != null) {
                        _loginResponse.value = loginResponse
                        dataStoreManager.saveToken(loginResponse.accessToken)
                        return@launch
                    }
                }
                _loginResponse.value = null
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

}