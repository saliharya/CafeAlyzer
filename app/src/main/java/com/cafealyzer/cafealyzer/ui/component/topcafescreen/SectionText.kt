package com.cafealyzer.cafealyzer.ui.component.topcafescreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SectionText(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.ExtraBold
        ),
        modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    )
}