package com.example.kakaobooksearchapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.kakaobooksearchapp.presentation.navigtation.KakaoBookNavHost
import com.example.kakaobooksearchapp.presentation.navigtation.model.navigateSingleTop
import com.example.kakaobooksearchapp.presentation.navigtation.model.rememberBottomNavItems
import com.example.kakaobooksearchapp.presentation.viewmodel.ThemeViewModel
import com.example.kakaobooksearchapp.ui.theme.KakaoBookSearchAppTheme
import com.example.kakaobooksearchapp.ui.theme.ThemeMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
            val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle(initialValue = false)
            var currentThemeMode by rememberSaveable { mutableStateOf(ThemeMode.LIGHT) }
            val borderColor = MaterialTheme.colorScheme.surfaceContainerHigh
            val bottomNavItems = rememberBottomNavItems()

            currentThemeMode = when (isDarkMode) {
                true -> ThemeMode.DARK
                false -> ThemeMode.LIGHT
            }

            KakaoBookSearchAppTheme(themeMode = currentThemeMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            modifier = Modifier
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
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        navController.navigateSingleTop(bottomAppBarNavItem.title)
                                    },
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
                ) { innerPadding ->
                    KakaoBookNavHost(
                        innerPadding = innerPadding,
                        navController = navController,
                        currentThemeMode = currentThemeMode,
                        onThemeModeChange = themeViewModel::setDarkMode,
                    )
                }
            }

            BackHandler {
                finishAffinity()
            }
        }
    }
}