package com.cafealyzer.cafealyzer.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafealyzer.cafealyzer.ui.viewmodel.UserViewModel

@Composable
fun ProfileScreen(userViewModel: UserViewModel = viewModel()) {
    val userData by userViewModel.userData.observeAsState()
    val isLoading by userViewModel.isLoading.observeAsState()

    LaunchedEffect(Unit) {
        if (userViewModel.isUserAuthenticated()) {
            userViewModel.fetchUserData()
        } else {
            Log.d("ProfileScreen", "Gagal")
        }
    }

    if (isLoading == true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    } else {
        userData?.let { user ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = "Username: ${user.username ?: "N/A"}", fontSize = 18.sp)
                Log.d("ProfileScreen", "ProfileScreen: ${user.username}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Email: ${user.email ?: "N/A"}", fontSize = 18.sp)
                Log.d("ProfileScreen", "ProfileScreen: ${user.username}")
            }
        }
    }
}