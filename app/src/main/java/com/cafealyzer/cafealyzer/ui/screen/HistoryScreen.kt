package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cafealyzer.cafealyzer.ui.component.historyscreen.CafeReviewCard
import com.cafealyzer.cafealyzer.ui.component.shimmer.HistoryScreenShimmering
import com.cafealyzer.cafealyzer.ui.navigation.Screen
import com.cafealyzer.cafealyzer.ui.viewmodel.HistoryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(historyViewModel: HistoryViewModel = viewModel(), navController: NavController) {
    val historyData by historyViewModel.historyData.observeAsState(emptyList())
    val isLoading by historyViewModel.isLoading.observeAsState()

    LaunchedEffect(Unit) {
        historyViewModel.getHistory()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Riwayat Review Cafe Anda",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        if (isLoading == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                HistoryScreenShimmering()
            }
        } else {
            if (historyData.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Center, // Center vertically
                    horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
                ) {
                    Text(
                        text = "Belum Ada Review Cafe Yang Disimpan",
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                items(historyData) { review ->
                    CafeReviewCard(
                        review = review,
                        onDetailClick = {
                            navController.navigate("${Screen.HistoryDetail.route}/${review.id}")
                        },
                        onDeleteClick = {
                            historyViewModel.deleteHistory(review.id)
                            historyViewModel.getHistory()
                            navController.navigate(Screen.Maps.route)
                        }
                    )
                }
            }
        }
    }
}

