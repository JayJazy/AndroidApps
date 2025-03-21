package com.example.kakaobooksearchapp.presentation.navigtation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kakaobooksearchapp.presentation.home.BookDetailItemScreen
import com.example.kakaobooksearchapp.presentation.home.BookListScreen
import com.example.kakaobooksearchapp.presentation.navigtation.model.BookNavItem
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel

@Composable
fun BookContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: BookViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BookNavItem.BookItemList.route
    ) {
        composable(BookNavItem.BookItemList.route) {
            BookListScreen(
                modifier = modifier
                    .fillMaxSize(),
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(BookNavItem.BookDetailItem.route) {
            BookDetailItemScreen(
                modifier = modifier
                    .fillMaxSize(),
                viewModel = viewModel
            )
        }
    }
}