package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cafealyzer.cafealyzer.R
import com.cafealyzer.cafealyzer.ui.component.authscreen.AppIcon
import com.cafealyzer.cafealyzer.ui.component.authscreen.EmailTextField
import com.cafealyzer.cafealyzer.ui.component.authscreen.LoginSection
import com.cafealyzer.cafealyzer.ui.component.authscreen.PasswordTextField
import com.cafealyzer.cafealyzer.ui.component.authscreen.RegisterButton
import com.cafealyzer.cafealyzer.ui.component.authscreen.UsernameTextField
import com.cafealyzer.cafealyzer.ui.navigation.Screen
import com.cafealyzer.cafealyzer.ui.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val registerViewModel: RegisterViewModel = hiltViewModel()
    val isRegisterSuccess by registerViewModel.registerSuccess.observeAsState()
    val isLoading by registerViewModel.isLoading.observeAsState()

    LaunchedEffect(isRegisterSuccess) {
        if (isRegisterSuccess == true) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Register.route) { inclusive = true }
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
        UsernameTextField(username = username) { username = it }
        Spacer(modifier = Modifier.height(16.dp))
        EmailTextField(email = email) { email = it }
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(password = password) { password = it }
        Spacer(modifier = Modifier.height(16.dp))
        RegisterButton(isLoading = isLoading, onClick = {
            registerViewModel.registerUser(email, username, password)
        })
        Spacer(modifier = Modifier.height(16.dp))
        LoginSection(navController = navController)
    }
}