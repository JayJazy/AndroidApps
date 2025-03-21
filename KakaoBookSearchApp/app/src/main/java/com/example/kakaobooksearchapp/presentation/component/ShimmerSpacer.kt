package com.example.kakaobooksearchapp.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerSpacer(
    isShimmerEffect: Boolean = false
) {
    if (isShimmerEffect) {
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
    }
}