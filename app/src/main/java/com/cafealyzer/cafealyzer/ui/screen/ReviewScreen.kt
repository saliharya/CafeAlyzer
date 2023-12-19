package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cafealyzer.cafealyzer.R
import com.cafealyzer.cafealyzer.model.CafeReview
import com.cafealyzer.cafealyzer.ui.component.historyscreen.CafeColumn
import com.cafealyzer.cafealyzer.ui.component.shimmer.DetailShimmerScreen
import com.cafealyzer.cafealyzer.ui.viewmodel.HistoryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(cafeReview: CafeReview) {
    val historyViewModel: HistoryViewModel = hiltViewModel()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(isLoading) {
        delay(2000)
        isLoading = false
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val localCoroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Review Detail",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            historyViewModel.postHistory(
                                cafeReview.cafeAnda,
                                cafeReview.cafeKompetitor,
                                cafeReview.positiveFeedbackAnda,
                                cafeReview.negativeFeedbackAnda,
                                cafeReview.positiveFeedbackKompetitor,
                                cafeReview.negativeFeedbackKompetitor,
                                cafeReview.suggestionsAnda
                            )
                            localCoroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Berhasil Menyimpan Review",
                                    actionLabel = "Tutup"
                                )
                            }
                        },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_save),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
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
            if (isLoading) {
                DetailShimmerScreen()
            } else {
                CafeColumn("Cafe Anda:", cafeReview.cafeAnda)
                Spacer(modifier = Modifier.height(8.dp))
                ReviewSection("Positif", cafeReview.positiveFeedbackAnda)
                Spacer(modifier = Modifier.height(8.dp))
                ReviewSection("Negatif", cafeReview.negativeFeedbackAnda)
                Spacer(modifier = Modifier.height(16.dp))
                CafeColumn("Cafe Kompetitor:", cafeReview.cafeKompetitor)
                Spacer(modifier = Modifier.height(8.dp))
                ReviewSection("Positif", cafeReview.positiveFeedbackKompetitor)
                Spacer(modifier = Modifier.height(8.dp))
                ReviewSection("Negatif", cafeReview.negativeFeedbackKompetitor)
                Spacer(modifier = Modifier.height(8.dp))
                ReviewSection(
                    "Saran yang dapat diberikan buat cafe anda",
                    cafeReview.suggestionsAnda
                )
            }
        }
    }
}

@Composable
fun ReviewSection(title: String, items: List<String>) {
    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        items.forEachIndexed { index, feedback ->
            Text(
                "${index + 1}. $feedback",
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}