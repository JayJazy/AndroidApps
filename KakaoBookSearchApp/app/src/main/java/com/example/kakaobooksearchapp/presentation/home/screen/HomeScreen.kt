package com.example.kakaobooksearchapp.presentation.home.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kakaobooksearchapp.presentation.component.HomeTopBar
import com.example.kakaobooksearchapp.presentation.navigtation.BookNavHost
import com.example.kakaobooksearchapp.presentation.navigtation.model.BookNavItem
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel

@Composable
fun HomeScreen(
    viewModel: BookViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isDetailScreen = currentRoute == BookNavItem.BookDetailItem.route
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            HomeTopBar(
                isDetailScreen = isDetailScreen,
                searchText = searchText,
                onSearchTextChange = viewModel::setSearchText,
                onSearchClick = viewModel::searchBookList,
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        BookNavHost(
            navController = navController,
            viewModel = viewModel,
            modifier = modifier
                .padding(top = innerPadding.calculateTopPadding() + 10.dp)
                .fillMaxSize(),
        )
    }
}