package com.cafealyzer.cafealyzer.ui.component.topcafescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color.Red
            )
            SectionText(title)
        }
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeSectionPreview() {
    HomeSection(title = "Sumedang") {

    }
}