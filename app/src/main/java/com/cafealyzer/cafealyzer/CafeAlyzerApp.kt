package com.cafealyzer.cafealyzer

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cafealyzer.cafealyzer.ui.theme.CafeAlyzerTheme

@Composable
fun CafeAlyzerApp() {
    Navigation()
}

@Preview
@Composable
fun CafeAlyzerAppPreview() {
    CafeAlyzerTheme {
        CafeAlyzerApp()
    }
}
