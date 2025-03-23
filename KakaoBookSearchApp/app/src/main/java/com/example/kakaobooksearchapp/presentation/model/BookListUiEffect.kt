package com.example.kakaobooksearchapp.presentation.model

sealed interface BookListUiEffect {
    data object OnClickBookDetail: BookListUiEffect
}