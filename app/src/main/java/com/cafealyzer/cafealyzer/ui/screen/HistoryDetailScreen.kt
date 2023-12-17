package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cafealyzer.cafealyzer.remote.response.ReviewData
import com.cafealyzer.cafealyzer.ui.component.historyscreen.CafeColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(cafeReview: ReviewData) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Review Detail")
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
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

@Composable
fun Section(title: String, items: List<String>) {
    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )
        items.forEachIndexed { index, feedback ->
            Text("${index + 1}. $feedback")
        }
    }
}