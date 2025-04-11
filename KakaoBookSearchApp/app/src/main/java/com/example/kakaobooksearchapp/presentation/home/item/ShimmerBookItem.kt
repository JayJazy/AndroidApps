package com.example.kakaobooksearchapp.presentation.home.item

import androidx.compose.foundation.lazy.grid.LazyGridScope
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.presentation.component.BookComponent

fun LazyGridScope.shimmerBookItem(
    bookDetail: Document,
    onBookClick: (Document) -> Unit
) {
    items(10) {
        BookComponent(
            bookDetail = bookDetail,
            onBookClick = onBookClick,
            isShimmerEffect = true,
        )
    }
}