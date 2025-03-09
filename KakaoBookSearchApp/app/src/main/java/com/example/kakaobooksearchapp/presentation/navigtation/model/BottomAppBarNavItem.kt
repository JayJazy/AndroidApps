package com.example.kakaobooksearchapp.presentation.navigtation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.kakaobooksearchapp.R

data class BottomAppBarNavItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun rememberBottomNavItems(): List<BottomAppBarNavItem> {
    return listOf(
        BottomAppBarNavItem(
            title = stringResource(id = R.string.home),
            icon = Icons.Filled.Home
        ),
        BottomAppBarNavItem(
            title = stringResource(id = R.string.setting),
            icon = Icons.Filled.Settings
        ),
    )
}

sealed class KakaoBookRoute(
    val route: String
) {
    data object Home: KakaoBookRoute(route = "홈")

    data object Setting: KakaoBookRoute(route = "설정")
}

fun NavController.navigateSingleTop(route: String) {
    this.navigate(route) {
        launchSingleTop = true
        popUpTo(graph.id) {
            inclusive = true
        }
    }
}