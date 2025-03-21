package com.example.kakaobooksearchapp.presentation.model

import com.example.kakaobooksearchapp.data.model.Document

sealed interface BookListUiEffect {
    data class OnClickBookDetail(val bookItem: Document): BookListUiEffect
}