package com.example.kakaobooksearchapp.presentation.navigtation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kakaobooksearchapp.presentation.home.screen.BookDetailScreen
import com.example.kakaobooksearchapp.presentation.home.screen.BookListScreen
import com.example.kakaobooksearchapp.presentation.navigtation.model.BookNavItem
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel

@Composable
fun BookNavHost(
    navController: NavHostController,
    viewModel: BookViewModel,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = BookNavItem.BookItemList.route
    ) {
        composable(BookNavItem.BookItemList.route) {
            BookListScreen(
                navController = navController,
                viewModel = viewModel,
                modifier = modifier
                    .fillMaxSize()
            )
        }

        composable(BookNavItem.BookDetailItem.route) {
            BookDetailScreen(
                viewModel = viewModel,
                modifier = modifier
                    .fillMaxSize()
            )
        }
    }
}