package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cafealyzer.cafealyzer.ui.component.historyscreen.CafeReviewCard
import com.cafealyzer.cafealyzer.ui.navigation.Screen
import com.cafealyzer.cafealyzer.ui.viewmodel.HistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(historyViewModel: HistoryViewModel = viewModel(), navController: NavController) {
    val historyData by historyViewModel.historyData.observeAsState()
    val isLoading by historyViewModel.isLoading.observeAsState()

    LaunchedEffect(Unit) {
        historyViewModel.getHistory()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "History Review Anda")
                }
            )
        }
    ) { innerPadding ->
        if (isLoading == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Red)
            }
        } else {
            historyData?.let {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                ) {
                    items(it) { review ->
                        CafeReviewCard(
                            review = review,
                            onDetailClick = {
                                navController.navigate("${Screen.HistoryDetail.route}/${review.id}")
                            },
                            onDeleteClick = {
                                // Delete Review
                            }
                        )
                    }
                }
            }
        }
    }
}