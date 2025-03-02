package com.example.kakaobooksearchapp.presentation.navigtation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomAppBarNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomNavItems = listOf(
    BottomAppBarNavItem(
        title = "좋아요",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Filled.Favorite
    ),
    BottomAppBarNavItem(
        title = "홈",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Filled.Home
    ),
    BottomAppBarNavItem(
        title = "설정",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Filled.Settings
    ),
)

sealed class KakaoBookRoute(
    val route: String
) {
    data object Favorite: KakaoBookRoute(route = bottomNavItems[0].title)

    data object Home: KakaoBookRoute(route = bottomNavItems[1].title)

    data object Setting: KakaoBookRoute(route = bottomNavItems[2].title)
}