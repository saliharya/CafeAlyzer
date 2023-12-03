package com.cafealyzer.cafealyzer.ui.component.authscreen

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun RegisterButton(isLoading: Boolean?, onClick: () -> Unit) {
    Button(onClick = onClick) {
        when (isLoading) {
            true -> CircularProgressIndicator(color = Color.White)
            else -> Text("Register")
        }
    }
}