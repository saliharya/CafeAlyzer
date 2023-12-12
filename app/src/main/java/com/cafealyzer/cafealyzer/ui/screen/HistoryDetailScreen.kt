package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cafealyzer.cafealyzer.model.CafeReview
import com.cafealyzer.cafealyzer.ui.component.historyscreen.CafeColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(cafeReview: CafeReview) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Review Detail")
                },
                actions = {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }
                },
            )
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CafeColumn("Cafe Anda:", cafeReview.cafeAnda)
            Spacer(modifier = Modifier.height(8.dp))
            Section("Positif", cafeReview.positiveFeedbackAnda)
            Spacer(modifier = Modifier.height(8.dp))
            Section("Negatif", cafeReview.negativeFeedbackAnda)
            Spacer(modifier = Modifier.height(16.dp))
            CafeColumn("Cafe Kompetitor:", cafeReview.cafeKompetitor)
            Spacer(modifier = Modifier.height(8.dp))
            Section("Positif", cafeReview.positiveFeedbackKompetitor)
            Spacer(modifier = Modifier.height(8.dp))
            Section("Negatif", cafeReview.negativeFeedbackKompetitor)
            Spacer(modifier = Modifier.height(8.dp))
            Section("Saran yang dapat diberikan buat cafe anda", cafeReview.suggestionsAnda)
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