package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cafealyzer.cafealyzer.ui.component.authscreen.AppIcon
import com.cafealyzer.cafealyzer.ui.component.authscreen.LoginButton
import com.cafealyzer.cafealyzer.ui.component.authscreen.PasswordTextField
import com.cafealyzer.cafealyzer.ui.component.authscreen.RegisterSection
import com.cafealyzer.cafealyzer.ui.component.authscreen.UsernameTextField
import com.cafealyzer.cafealyzer.ui.navigation.Screen
import com.cafealyzer.cafealyzer.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginViewModel: LoginViewModel = hiltViewModel()
    val isLoginSuccess by loginViewModel.loginSuccess.observeAsState()
    val isLoading by loginViewModel.isLoading.observeAsState()

    LaunchedEffect(isLoginSuccess) {
        if (isLoginSuccess == true) {
            navController.navigate(Screen.Maps.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppIcon()
        Spacer(modifier = Modifier.height(16.dp))
        UsernameTextField(username = username, onUsernameChange = { username = it })
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(password = password) { password = it }
        Spacer(modifier = Modifier.height(16.dp))
        LoginButton(isLoading = isLoading) {
            loginViewModel.loginUser(username, password)
        }
        Spacer(modifier = Modifier.height(16.dp))
        RegisterSection(navController = navController)
    }
}










