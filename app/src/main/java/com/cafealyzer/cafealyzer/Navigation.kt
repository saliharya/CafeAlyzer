package com.cafealyzer.cafealyzer

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cafealyzer.cafealyzer.model.generateDummyReviews
import com.cafealyzer.cafealyzer.ui.component.topcafescreen.BottomBar
import com.cafealyzer.cafealyzer.ui.navigation.Screen
import com.cafealyzer.cafealyzer.ui.screen.CompareCafeScreen
import com.cafealyzer.cafealyzer.ui.screen.HistoryDetailScreen
import com.cafealyzer.cafealyzer.ui.screen.HistoryScreen
import com.cafealyzer.cafealyzer.ui.screen.LoginScreen
import com.cafealyzer.cafealyzer.ui.screen.MapsScreen
import com.cafealyzer.cafealyzer.ui.screen.ProfileScreen
import com.cafealyzer.cafealyzer.ui.screen.RegisterScreen
import com.cafealyzer.cafealyzer.ui.screen.ReviewScreen
import com.cafealyzer.cafealyzer.ui.screen.SearchScreen
import com.cafealyzer.cafealyzer.ui.screen.TopCafeScreen
import com.cafealyzer.cafealyzer.ui.viewmodel.HistoryViewModel
import com.cafealyzer.cafealyzer.ui.viewmodel.MapsViewModel
import com.cafealyzer.cafealyzer.ui.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(token: String?, navController: NavHostController) {
    val userViewModel: UserViewModel = hiltViewModel()
    val mapsViewModel: MapsViewModel = hiltViewModel()
    val historyViewModel: HistoryViewModel = hiltViewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isProfileScreen = currentRoute == Screen.Profile.route
    val isDetailScreen = currentRoute == Screen.History.route
    val isTopCafeScreen = currentRoute == Screen.TopCafe.route
    val isMapScreen = currentRoute == Screen.Maps.route
    Scaffold(
        bottomBar = {
            if (isProfileScreen || isDetailScreen || isTopCafeScreen || isMapScreen) {
                BottomBar(navController)
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (token != null) Screen.Maps.route else Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Maps.route) {
                MapsScreen(mapsViewModel = mapsViewModel, navController = navController)
            }
            composable(Screen.History.route) {
                HistoryScreen(historyViewModel, navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(userViewModel = userViewModel, onLogoutClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Maps.route) {
                            inclusive = true
                        }
                    }
                })
            }
            composable(Screen.TopCafe.route) {
                TopCafeScreen()
            }
            composable(Screen.Search.route) {
                SearchScreen(mapsViewModel) { selectedCafe ->
                    mapsViewModel.setSelectedCafe(selectedCafe)
                    navController.navigate(Screen.Maps.route) {
                        launchSingleTop = true
                        popUpTo(Screen.Search.route) {
                            inclusive = true
                        }
                    }
                }
            }
            composable(Screen.Login.route) {
                LoginScreen(navController = navController)
            }
            composable(Screen.Register.route) {
                RegisterScreen(navController = navController)
            }
            composable("${Screen.HistoryDetail.route}/{reviewId}") { backStackEntry ->
                val reviewId = backStackEntry.arguments?.getString("reviewId")
                val review = historyViewModel.historyData.value?.find { it.id == reviewId }
                if (review != null) {
                    HistoryDetailScreen(review)
                }
            }
            composable("${Screen.ReviewCafe.route}/{reviewId}") { backStackEntry ->
                val reviewId = backStackEntry.arguments?.getString("reviewId")?.toIntOrNull()
                val review = generateDummyReviews().find { it.id == reviewId }
                if (review != null) {
                    ReviewScreen(review)
                }
            }
            composable("${Screen.CompareCafe.route}/{cafeName1}/{cafeName2}") { backStackEntry ->
                val cafeName1 = backStackEntry.arguments?.getString("cafeName1") ?: ""
                val cafeName2 = backStackEntry.arguments?.getString("cafeName2") ?: ""

                CompareCafeScreen(
                    mapsViewModel = mapsViewModel,
                    cafeName1 = cafeName1,
                    cafeName2 = cafeName2,
                    navController = navController
                )
            }
        }
    }
}
