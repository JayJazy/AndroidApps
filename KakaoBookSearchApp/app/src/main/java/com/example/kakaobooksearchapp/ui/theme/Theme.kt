package com.example.kakaobooksearchapp.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    background = ThemeColors.Dark.backGround,
    onPrimary = ThemeColors.Dark.onPrimary,
    surface = ThemeColors.Dark.surface,
    surfaceContainerHigh = ThemeColors.Dark.surfaceContainerHigh,
    onSurface = ThemeColors.Dark.onSurface,
    primary = ThemeColors.Dark.text,
    secondary = ThemeColors.Dark.secondary,
    onErrorContainer = ThemeColors.Dark.onErrorContainer,
    error = ThemeColors.Dark.error
)

private val LightColorScheme = lightColorScheme(
    background = ThemeColors.Light.backGround,
    onPrimary = ThemeColors.Light.onPrimary,
    surface = ThemeColors.Light.surface,
    surfaceContainerHigh = ThemeColors.Light.surfaceContainerHigh,
    onSurface = ThemeColors.Light.onSurface,
    primary = ThemeColors.Light.text,
    secondary = ThemeColors.Light.secondary,
    onErrorContainer = ThemeColors.Light.onErrorContainer,
    error = ThemeColors.Light.error
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

    val lightColorScheme = LightColorScheme
    val darkColorScheme = DarkColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            if(themeMode == ThemeMode.LIGHT) window.statusBarColor = lightColorScheme.background.toArgb()
            else window.statusBarColor = darkColorScheme.background.toArgb()
            WindowCompat.setDecorFitsSystemWindows(window, true)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = lightTheme
        }
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