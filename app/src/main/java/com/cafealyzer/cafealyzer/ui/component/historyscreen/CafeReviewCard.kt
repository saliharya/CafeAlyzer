package com.cafealyzer.cafealyzer.ui.component.historyscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cafealyzer.cafealyzer.remote.response.ReviewData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CafeReviewCard(review: ReviewData, onDetailClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            CafeInfo(review = review)
            val reviewDate = review.date.toDate()
            Text(
                text = "Tanggal: ${reviewDate.toFormattedString("dd-MM-yyyy HH:mm:ss")}",
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onDetailClick) {
                    Icon(imageVector = Icons.Default.Info, contentDescription = "Detail")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}

@Composable
fun CafeInfo(review: ReviewData) {
    CafeColumn(text = "Cafe Anda:", value = review.cafeUser)
    Spacer(modifier = Modifier.height(8.dp))
    CafeColumn(text = "Cafe Kompetitor:", value = review.cafeCompetitor)
}

@Composable
fun CafeColumn(text: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
            Text(text = value)
        }
    }
}

fun String.toDate(): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
    return dateFormat.parse(this) ?: Date()
}

fun Date.toFormattedString(pattern: String): String {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return dateFormat.format(this)
}