package com.example.kakaobooksearchapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = ThemeColors.Night.backGround,
    onPrimary = ThemeColors.Night.onPrimary,
    surface = ThemeColors.Night.surface,
    surfaceContainerHigh = ThemeColors.Night.surfaceContainerHigh,
    primary = ThemeColors.Night.text,
)

private val LightColorScheme = lightColorScheme(
    background = ThemeColors.Day.backGround,
    onPrimary = ThemeColors.Day.onPrimary,
    surface = ThemeColors.Day.surface,
    surfaceContainerHigh = ThemeColors.Day.surfaceContainerHigh,
    primary = ThemeColors.Day.text,
)

@Composable
fun KakaoBookSearchAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}