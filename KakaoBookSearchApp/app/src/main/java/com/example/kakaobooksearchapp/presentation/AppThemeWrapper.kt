package com.example.kakaobooksearchapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kakaobooksearchapp.presentation.viewmodel.ThemeViewModel
import com.example.kakaobooksearchapp.ui.theme.KakaoBookSearchAppTheme
import com.example.kakaobooksearchapp.ui.theme.ThemeMode

@Composable
fun AppThemeWrapper(
    themeViewModel: ThemeViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle(initialValue = false)
    val currentThemeMode = if (isDarkMode) ThemeMode.DARK else ThemeMode.LIGHT

    KakaoBookSearchAppTheme(themeMode = currentThemeMode) {
        content()
    }
}