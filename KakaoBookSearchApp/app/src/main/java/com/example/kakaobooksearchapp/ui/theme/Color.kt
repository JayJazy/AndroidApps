package com.example.kakaobooksearchapp.ui.theme

import androidx.compose.ui.graphics.Color

val orange = Color(0xFFFFA500)
val light_gray = Color(0xFFA9A9A9)
val dark_bg = Color(0xFF353739)
val image_bg = Color(0xFFE8E8E8)
val light_line = Color(0xFFCBCBCB)
val red = Color.Red

sealed class ThemeColors(
    val backGround: Color,
    val onPrimary: Color,
    val surface: Color,
    val surfaceContainerHigh: Color,
    val onSurface: Color,
    val text: Color,
    val secondary: Color,
)  {
    data object Dark: ThemeColors(
        backGround = dark_bg,
        onPrimary = orange,
        surface = light_gray,
        surfaceContainerHigh = light_line,
        onSurface = image_bg,
        text = Color.White,
        secondary = red
    )
    data object Light: ThemeColors(
        backGround = Color.White,
        onPrimary = orange,
        surface = light_gray,
        surfaceContainerHigh = light_gray,
        onSurface = image_bg,
        text = Color.Black,
        secondary = red
    )
}