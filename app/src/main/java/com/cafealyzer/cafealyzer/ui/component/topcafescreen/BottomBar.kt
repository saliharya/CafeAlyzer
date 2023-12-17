package com.cafealyzer.cafealyzer.ui.component.topcafescreen

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cafealyzer.cafealyzer.R
import com.cafealyzer.cafealyzer.ui.navigation.NavigationItem
import com.cafealyzer.cafealyzer.ui.navigation.Screen

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = "Peta", icon = R.drawable.ic_home, screen = Screen.Maps
            ),
            NavigationItem(
                title = "Top Cafe", icon = R.drawable.ic_thumbup, screen = Screen.TopCafe
            ),
            NavigationItem(
                title = "Riwayat", icon = R.drawable.ic_history, screen = Screen.History
            ),
            NavigationItem(
                title = "Profil", icon = R.drawable.ic_person, screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(icon = {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = item.title
                )
            }, label = {
                Text(item.title)
            }, selected = currentRoute == item.screen.route, onClick = {
                navController.navigate(item.screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            })
        }
    }
}