package com.example.kakaobooksearchapp.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.example.kakaobooksearchapp.presentation.model.BottomAppBarNavItem

@Composable
fun MainBottomBar(
    selectedIndex: Int,
    bottomNavItems: List<BottomAppBarNavItem>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = MaterialTheme.colorScheme.surfaceContainerHigh

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = borderColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(top = 1.dp),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        bottomNavItems.forEachIndexed{ index, bottomAppBarNavItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onClick() },
                label = {
                    Text(
                        text = bottomAppBarNavItem.title
                    )
                },
                icon = {
                    Icon(
                        imageVector = bottomAppBarNavItem.icon,
                        contentDescription = bottomAppBarNavItem.title
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIndicatorColor = MaterialTheme.colorScheme.background,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.surface,
                    unselectedTextColor = MaterialTheme.colorScheme.primary,
                    disabledIconColor = MaterialTheme.colorScheme.surface,
                    disabledTextColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    }
}