package com.example.kakaobooksearchapp.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.kakaobooksearchapp.data.model.Document

@Stable
sealed class DetailBookState {

    @Immutable
    data object Loading: DetailBookState()

    @Immutable
    data object Error: DetailBookState()

    @Immutable
    data class Success(val bookDetail: Document): DetailBookState()
}