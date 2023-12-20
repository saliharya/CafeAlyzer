package com.cafealyzer.cafealyzer.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.cafealyzer.cafealyzer.BuildConfig
import com.cafealyzer.cafealyzer.model.generateDummyReviews
import com.cafealyzer.cafealyzer.remote.response.CafeDetailResponse
import com.cafealyzer.cafealyzer.remote.response.CafeReviewsItem
import com.cafealyzer.cafealyzer.ui.navigation.Screen
import com.cafealyzer.cafealyzer.ui.viewmodel.MapsViewModel
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareCafeScreen(
    mapsViewModel: MapsViewModel = viewModel(),
    cafeName1: String,
    cafeName2: String,
    navController: NavController,
) {
    val cafeDetail1 by mapsViewModel.cafeDetail.observeAsState()
    val cafeDetail2 by mapsViewModel.cafeDetail2.observeAsState()
    val reviewList = generateDummyReviews()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail Cafe Yang Akan Dibanding",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                CompareCafeCard("Cafe Anda :", cafeName1, cafeDetail1)
                CompareCafeCard("Cafe Kompetitor :", cafeName2, cafeDetail2)
            }
            item(reviewList) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        navController.navigate("${Screen.ReviewCafe.route}/${reviewList[0].id}")
                    }) {
                    Text(text = "Bandingkan")
                }
            }
        }
    }
}

@Composable
fun CompareCafeCard(title: String, name: String, cafeDetail: CafeDetailResponse?) {
    Card(
        modifier = Modifier.padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
    ) {
        Text(text = title, modifier = Modifier.padding(start = 8.dp, top = 8.dp))
        Text(
            text = name,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold,
            style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp)
        )
        cafeDetail?.let { detail ->
            val photoReference = detail.result.photos[0].photoReference
            AsyncImage(
                model = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=$photoReference&key=${BuildConfig.KEY}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentDescription = "Image of $name"
            )
            Text(
                text = "Beberapa Review Dari Pengunjung",
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.SemiBold
            )
            // Display only three reviews
            Column {
                repeat(min(3, detail.result.reviews.size)) { index ->
                    val review = detail.result.reviews[index]
                    ReviewItem(review = review)
                }
            }
        }
    }
}

@Composable
fun ReviewItem(review: CafeReviewsItem) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.onSecondaryContainer, RoundedCornerShape(8.dp))
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = if (expanded) review.text else review.text.take(100) + "...",
            modifier = Modifier.padding(8.dp)
        )
    }
}