package com.example.kakaobooksearchapp.presentation.navigtation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kakaobooksearchapp.presentation.bookmark.BookmarkScreen
import com.example.kakaobooksearchapp.presentation.home.HomeScreen
import com.example.kakaobooksearchapp.presentation.setting.SettingScreen
import com.example.kakaobooksearchapp.presentation.navigtation.model.KakaoBookRoute
import com.example.kakaobooksearchapp.ui.theme.ThemeMode

@Composable
fun KakaoBookContent(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    navController: NavHostController,
    currentThemeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = KakaoBookRoute.Home.route,
        modifier = modifier.padding(innerPadding)
    ) {
        composable(route = KakaoBookRoute.Home.route) {
            HomeScreen(
                modifier = modifier
            )
        }

        composable(route = KakaoBookRoute.Bookmark.route) {
            BookmarkScreen()
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