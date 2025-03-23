package com.example.kakaobooksearchapp.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import com.example.kakaobooksearchapp.data.model.Document
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Stable
sealed class BookListState{

    @Immutable
    data object Loading: BookListState()

    @Immutable
    data object Error: BookListState()

    @Immutable
    data class Success(
        val bookList: Flow<PagingData<Document>> = emptyFlow(),
        val bookDetailItem: Document? = null
    ): BookListState()
}