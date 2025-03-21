package com.example.kakaobooksearchapp.presentation.navigtation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kakaobooksearchapp.presentation.home.BookDetailItemScreen
import com.example.kakaobooksearchapp.presentation.home.BookItemListScreen
import com.example.kakaobooksearchapp.presentation.navigtation.model.BookNavItem
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel

@Composable
fun BookItemListContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: BookViewModel = hiltViewModel()
) {

    NavHost(
        navController = navController,
        startDestination = BookNavItem.BookItemList.route
    ) {
        composable(BookNavItem.BookItemList.route) {
            BookItemListScreen(
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