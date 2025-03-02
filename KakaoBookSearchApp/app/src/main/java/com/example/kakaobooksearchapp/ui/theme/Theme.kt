package com.example.kakaobooksearchapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = ThemeColors.Dark.backGround,
    onPrimary = ThemeColors.Dark.onPrimary,
    surface = ThemeColors.Dark.surface,
    surfaceContainerHigh = ThemeColors.Dark.surfaceContainerHigh,
    primary = ThemeColors.Dark.text,
)

private val LightColorScheme = lightColorScheme(
    background = ThemeColors.Light.backGround,
    onPrimary = ThemeColors.Light.onPrimary,
    surface = ThemeColors.Light.surface,
    surfaceContainerHigh = ThemeColors.Light.surfaceContainerHigh,
    primary = ThemeColors.Light.text,
)

@Composable
fun KakaoBookSearchAppTheme(
    themeMode: ThemeMode,
    content: @Composable () -> Unit
) {
    val lightTheme = when (themeMode) {
        ThemeMode.LIGHT -> true
        ThemeMode.DARK -> false
    }

    val colorScheme = if (lightTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

enum class ThemeMode {
    LIGHT, DARK
}