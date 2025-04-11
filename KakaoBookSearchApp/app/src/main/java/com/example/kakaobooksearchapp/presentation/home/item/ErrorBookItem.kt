package com.example.kakaobooksearchapp.presentation.home.item

import androidx.compose.foundation.lazy.grid.LazyGridScope
import com.example.kakaobooksearchapp.presentation.component.ErrorDialog

fun LazyGridScope.errorBookItem(
    onRefresh: () -> Unit
) {
    item {
        ErrorDialog(requestBookList = onRefresh)
    }
}