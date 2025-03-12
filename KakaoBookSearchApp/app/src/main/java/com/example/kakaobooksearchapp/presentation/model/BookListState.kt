package com.example.kakaobooksearchapp.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.kakaobooksearchapp.data.model.Document

@Stable
sealed class BookListState{

    @Immutable
    data object Loading: BookListState()

    @Immutable
    data object Error: BookListState()

    @Immutable
    data class Success(
        val bookList: List<Document> = emptyList()
    ): BookListState()
}