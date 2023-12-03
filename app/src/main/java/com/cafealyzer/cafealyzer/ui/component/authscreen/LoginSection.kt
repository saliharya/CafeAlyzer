package com.cafealyzer.cafealyzer.ui.component.authscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.cafealyzer.cafealyzer.ui.navigation.Screen

@Composable
fun LoginSection(navController: NavHostController) {
    Row {
        Text(text = "Already have an account?")
        Text(
            text = " Login ",
            modifier = Modifier.clickable {
                navController.navigate(Screen.Login.route)
            },
            color = MaterialTheme.colorScheme.primary
        )
    }
}