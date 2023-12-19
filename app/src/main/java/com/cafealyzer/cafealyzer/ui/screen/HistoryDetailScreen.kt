package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cafealyzer.cafealyzer.remote.response.ReviewData
import com.cafealyzer.cafealyzer.ui.component.historyscreen.CafeColumn
import com.cafealyzer.cafealyzer.ui.component.shimmer.DetailShimmerScreen
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(cafeReview: ReviewData) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(isLoading) {
        delay(2000)
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Review Detail",
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (isLoading) {
                DetailShimmerScreen()
            } else {
                CafeColumn("Cafe Anda:", cafeReview.cafeUser)
                Spacer(modifier = Modifier.height(8.dp))
                Section("Positif", cafeReview.positiveFeedback)
                Spacer(modifier = Modifier.height(8.dp))
                Section("Negatif", cafeReview.negativeFeedback)
                Spacer(modifier = Modifier.height(16.dp))
                CafeColumn("Cafe Kompetitor:", cafeReview.cafeCompetitor)
                Spacer(modifier = Modifier.height(8.dp))
                Section("Positif", cafeReview.positiveFeedbackCompetitor)
                Spacer(modifier = Modifier.height(8.dp))
                Section("Negatif", cafeReview.negativeFeedbackCompetitor)
                Spacer(modifier = Modifier.height(8.dp))
                Section("Saran yang dapat diberikan buat cafe anda", cafeReview.suggestion)
            }
        }
    }
}

@Composable
fun Section(title: String, items: List<String>) {
    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        items.forEachIndexed { index, feedback ->
            Text(
                "${index + 1}. $feedback",
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}