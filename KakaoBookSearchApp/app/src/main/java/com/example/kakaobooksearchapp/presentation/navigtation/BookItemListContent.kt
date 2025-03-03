package com.example.kakaobooksearchapp.presentation.navigtation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kakaobooksearchapp.presentation.home.BookDetailItemScreen
import com.example.kakaobooksearchapp.presentation.home.BookItemListScreen
import com.example.kakaobooksearchapp.presentation.home.BookSearchItemListScreen
import com.example.kakaobooksearchapp.presentation.navigtation.model.BookNavItem
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel

@Composable
fun BookItemListContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    innerPadding: PaddingValues,
    isDetailScreen: Boolean,
    viewModel: BookViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.onClickItem.collect { route ->
            navController.navigate(route)
        }
    }

    NavHost(
        navController = navController,
        startDestination = BookNavItem.BookItemList.route
    ) {
        composable(BookNavItem.BookItemList.route) {
            BookItemListScreen(
                modifier = modifier,
                onItemClick = viewModel::onItemClick
            )
        }

        composable(BookNavItem.BookSearchItemList.route) {
            BookSearchItemListScreen(
                modifier = modifier,
                onItemClick = viewModel::onItemClick
            )
        }

        composable(BookNavItem.BookDetailItem.route) {
            BookDetailItemScreen(
                modifier = modifier
                    .padding(innerPadding),
            )
        }
    }
}