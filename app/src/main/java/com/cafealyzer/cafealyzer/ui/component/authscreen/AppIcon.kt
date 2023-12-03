package com.cafealyzer.cafealyzer.ui.component.authscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cafealyzer.cafealyzer.R

@Composable
fun AppIcon() {
    Image(
        painter = painterResource(id = R.mipmap.ic_launcher_foreground),
        contentDescription = "App Launcher Icon",
        modifier = Modifier.size(250.dp)
    )
}