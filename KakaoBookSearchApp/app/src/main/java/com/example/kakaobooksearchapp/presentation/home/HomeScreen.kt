package com.example.kakaobooksearchapp.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kakaobooksearchapp.presentation.component.HomeTopBar
import com.example.kakaobooksearchapp.presentation.navigtation.BookContent
import com.example.kakaobooksearchapp.presentation.navigtation.model.BookNavItem
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isDetailScreen = currentRoute == BookNavItem.BookDetailItem.route

    Scaffold(
        topBar = {
            HomeTopBar(
                modifier = Modifier,
                isDetailScreen = isDetailScreen,
                setSearchText = viewModel::setSearchText,
                onSearchClick = viewModel::getBookList,
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        BookContent(
            modifier = modifier
                .padding(top = innerPadding.calculateTopPadding() + 10.dp)
                .fillMaxSize(),
            navController = navController,
            viewModel = viewModel
        )
    }
}