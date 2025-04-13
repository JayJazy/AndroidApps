package com.example.kakaobooksearchapp.presentation.home.item

import androidx.compose.foundation.lazy.grid.LazyGridScope
import com.example.kakaobooksearchapp.presentation.component.ErrorDialog

fun LazyGridScope.errorBookItem(
    onRequestClick: () -> Unit
) {
    item {
        ErrorDialog(onRequestClick = onRequestClick)
    }
}