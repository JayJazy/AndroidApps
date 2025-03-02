package com.example.kakaobooksearchapp.presentation.navigtation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kakaobooksearchapp.presentation.home.BookDetailItemScreen
import com.example.kakaobooksearchapp.presentation.home.BookItemListScreen
import com.example.kakaobooksearchapp.presentation.home.BookSearchItemListScreen
import com.example.kakaobooksearchapp.presentation.navigtation.model.BookNavItem

@Composable
fun BookItemListContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues,
    isDetailScreen: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = BookNavItem.BookItemList.route
    ) {
        composable(BookNavItem.BookItemList.route) {
            BookItemListScreen(
                modifier = modifier
                    .padding(top = 40.dp)
            )
        }

        composable(BookNavItem.BookSearchItemList.route) {
            BookSearchItemListScreen(
                modifier = modifier
                    .padding(top = 40.dp)
            )
        }

        composable(BookNavItem.BookDetailItem.route) {
            BookDetailItemScreen(
                modifier = modifier
                    .padding(top = 40.dp)
            )
        }
    }
}