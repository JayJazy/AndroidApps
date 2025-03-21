package com.example.kakaobooksearchapp.presentation.navigtation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kakaobooksearchapp.presentation.home.HomeScreen
import com.example.kakaobooksearchapp.presentation.setting.SettingScreen
import com.example.kakaobooksearchapp.presentation.navigtation.model.KakaoBookRoute
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel
import com.example.kakaobooksearchapp.ui.theme.ThemeMode

@Composable
fun KakaoBookContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    navController: NavHostController,
    currentThemeMode: ThemeMode,
    onThemeModeChange: (Boolean) -> Unit,
    viewModel: BookViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = KakaoBookRoute.Home.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(route = KakaoBookRoute.Home.route) {
            HomeScreen(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = innerPadding.calculateBottomPadding()),
                viewModel = viewModel
            )
        }

        composable(route = KakaoBookRoute.Setting.route) {
            SettingScreen(
                modifier = modifier,
                currentThemeMode = currentThemeMode,
                onThemeModeChange = onThemeModeChange
            )
        }
    }
}