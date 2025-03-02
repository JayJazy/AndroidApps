package com.example.kakaobooksearchapp.presentation.navigtation.model

sealed class BookNavItem(
    val route: String
) {
    data object BookItemList: BookNavItem(route = "bookItemList")

    data object BookSearchItemList: BookNavItem(route = "bookSearchItemList")

    data object BookDetailItem: BookNavItem(route = "bookDetailItem")
}