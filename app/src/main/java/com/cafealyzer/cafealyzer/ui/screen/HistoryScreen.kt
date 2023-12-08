package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.cafealyzer.cafealyzer.model.generateDummyReviews
import com.cafealyzer.cafealyzer.ui.component.historyscreen.CafeReviewCard
import com.cafealyzer.cafealyzer.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController) {
    val reviewList = generateDummyReviews()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "History Review Anda")
                }
            )
        }
    ) { innerpadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerpadding)
        ) {
            items(reviewList) { review ->
                CafeReviewCard(
                    review = review,
                    onDetailClick = {
                        navController.navigate("${Screen.HistoryDetail.route}/${review.id}")
                    },
                    onDeleteClick = {}
                )
            }
        }
    }
}