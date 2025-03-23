package com.example.kakaobooksearchapp.presentation.home.item

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.ui.Modifier
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.presentation.component.BookItem

fun LazyGridScope.shimmerBook(
    modifier: Modifier = Modifier,
    bookData: Document,
    onSetBookDetailItem: (Document) -> Unit
) {
    items(10) {
        BookItem(
            modifier = modifier,
            isShimmerEffect = true,
            bookData = bookData,
            onSetBookDetailItem = onSetBookDetailItem
        )
    }
}