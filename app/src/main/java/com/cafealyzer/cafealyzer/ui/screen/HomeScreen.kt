package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.cafealyzer.cafealyzer.model.dummyCafeJakarta
import com.cafealyzer.cafealyzer.model.dummyCafeSumedang
import com.cafealyzer.cafealyzer.ui.component.topcafescreen.HomeSection
import com.cafealyzer.cafealyzer.ui.component.topcafescreen.TrendingCafeRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopCafeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Top Rated Cafe Sekarang",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            HomeSection(
                title = "Sumedang"
            ) { TrendingCafeRow(dummyCafeSumedang) }
            HomeSection(
                title = "Jakarta"
            ) { TrendingCafeRow(dummyCafeJakarta) }
        }
    }
}