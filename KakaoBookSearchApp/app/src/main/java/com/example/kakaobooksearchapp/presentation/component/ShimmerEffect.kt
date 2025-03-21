package com.example.kakaobooksearchapp.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor

@Composable
fun shimmerEffect(): Brush {
    val baseColor = MaterialTheme.colorScheme.surface

    val transition = rememberInfiniteTransition(label = "shimmer_transition")

    val alphaAnim = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer_alpha_animation"
    )

    val shimmerColor = baseColor.copy(alpha = alphaAnim.value)

    return SolidColor(shimmerColor)
}