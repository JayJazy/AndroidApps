package com.example.kakaobooksearchapp.presentation.home.item

import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.paging.compose.LazyPagingItems
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.presentation.component.BookItem

fun LazyGridScope.existBook(
    itemCount: Int,
    bookList: LazyPagingItems<Document>,
    onSetBookDetailItem: (Document) -> Unit
) {
    items(
        count = itemCount,
        key = { index -> bookList[index]?.isbn ?: index.toString() }
    ) {
        bookList[it]?.let { bookData ->
            BookItem(
                isShimmerEffect = false,
                bookData = bookData,
                onSetBookDetailItem = onSetBookDetailItem
            )
        }
    }
}