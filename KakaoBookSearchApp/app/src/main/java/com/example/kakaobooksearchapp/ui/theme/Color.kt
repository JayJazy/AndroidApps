package com.example.kakaobooksearchapp.ui.theme

import androidx.compose.ui.graphics.Color

val orange = Color(0xFFFFA500)
val light_gray = Color(0xFFA9A9A9)
val dark_bg = Color(0xFF1A1C1E)
val light_line = Color(0xFFCBCBCB)

sealed class ThemeColors(
    val backGround: Color,
    val onPrimary: Color,
    val surface: Color,
    val surfaceContainerHigh: Color,
    val text: Color,

)  {
    data object Night: ThemeColors(
        backGround = dark_bg,
        onPrimary = orange,
        surface = light_gray,
        surfaceContainerHigh = light_line,
        text = Color.White
    )
    data object Day: ThemeColors(
        backGround = Color.White,
        onPrimary = orange,
        surface = light_gray,
        surfaceContainerHigh = light_gray,
        text = Color.Black
    )
}