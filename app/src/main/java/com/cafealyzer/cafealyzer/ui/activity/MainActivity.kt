package com.cafealyzer.cafealyzer.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cafealyzer.cafealyzer.local.DataStoreManager
import com.cafealyzer.cafealyzer.ui.navigation.Screen
import com.cafealyzer.cafealyzer.ui.screen.HistoryScreen
import com.cafealyzer.cafealyzer.ui.screen.HomeScreen
import com.cafealyzer.cafealyzer.ui.screen.LoginScreen
import com.cafealyzer.cafealyzer.ui.screen.MapsScreen
import com.cafealyzer.cafealyzer.ui.screen.ProfileScreen
import com.cafealyzer.cafealyzer.ui.screen.RegisterScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val dataStoreManager = DataStoreManager(this)
            var startDestination by remember { mutableStateOf("login") }

            LaunchedEffect(key1 = true) {
                val token = dataStoreManager.getToken()
                startDestination = if (token != null) "home" else "login"
            }

            NavHost(navController = navController, startDestination = startDestination) {
                composable("login") { LoginScreen(navController) }
                composable("home") {
                    HomeScreen(onCariClick = {

                    })
                }
                composable("register") {
                    RegisterScreen(navController = navController)
                }
                composable("history") { HistoryScreen() }
                composable("profile") { ProfileScreen() }
                composable("maps") { MapsScreen() }
            }
        }
    }
}

