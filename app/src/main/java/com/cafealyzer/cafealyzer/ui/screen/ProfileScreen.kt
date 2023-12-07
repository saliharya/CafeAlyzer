package com.cafealyzer.cafealyzer.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafealyzer.cafealyzer.ui.component.profilescreen.ProfileContent
import com.cafealyzer.cafealyzer.ui.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(userViewModel: UserViewModel = viewModel(), onLogoutClick: () -> Unit) {
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
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Red)
        }
    } else {
        userData?.let { user ->
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Profil Anda")
                        }
                    )
                },
                content = { innerpadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerpadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileContent(user = user)
                        Button(
                            onClick = {
                                userViewModel.logoutUser()
                                onLogoutClick()
                            },
                        ) {
                            Text("Logout")
                        }
                    }
                }
            )
        }
    }
}