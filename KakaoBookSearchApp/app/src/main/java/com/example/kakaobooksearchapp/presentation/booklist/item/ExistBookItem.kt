package com.example.kakaobooksearchapp.presentation.booklist.item

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.paging.compose.LazyPagingItems
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.presentation.component.BookComponent

fun LazyGridScope.existBookItem(
    itemCount: Int,
    bookList: LazyPagingItems<Document>,
    onBookClick: (Document) -> Unit,
) {
    items(
        count = itemCount,
        key = { index -> bookList[index]?.isbn ?: index.toString() }
    ) { index ->
        bookList[index]?.let { bookDetail ->
            BookComponent(
                bookDetail = bookDetail,
                onBookClick = onBookClick,
                isShimmerEffect = false,
            )
        }
    }
}