package com.example.kakaobooksearchapp.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface BookListUiEffect {
    @Immutable
    data object ToastEmptySearchText: BookListUiEffect

    @Immutable
    data class OnClickBookDetail(val isbn: String): BookListUiEffect

    @Immutable
    data object OnClickBookSort: BookListUiEffect
}