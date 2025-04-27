package com.example.kakaobooksearchapp.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
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