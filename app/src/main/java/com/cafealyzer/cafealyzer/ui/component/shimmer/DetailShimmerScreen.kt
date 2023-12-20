package com.cafealyzer.cafealyzer.ui.component.shimmer

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DetailShimmerScreen() {
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.primary,
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    DetailShimmerItem(brush = brush)
}

@Composable
fun DetailShimmerItem(brush: Brush) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 0.3f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 0.6f)
                .background(brush)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 0.2f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 0.2f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 0.4f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 0.6f)
                .background(brush)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 0.2f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 0.2f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 0.8f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth(fraction = 1f)
                .background(brush)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun DetailShimmerItemPreview() {
    DetailShimmerItem(
        brush = Brush.linearGradient(
            listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.surface,
                MaterialTheme.colorScheme.primary,
            )
        )
    )
}
