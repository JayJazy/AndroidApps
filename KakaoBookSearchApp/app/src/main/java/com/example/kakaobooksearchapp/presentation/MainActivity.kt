package com.example.kakaobooksearchapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kakaobooksearchapp.presentation.booklist.screen.BookListScreen
import com.example.kakaobooksearchapp.presentation.component.MainBottomBar
import com.example.kakaobooksearchapp.presentation.model.rememberBottomNavItems
import com.example.kakaobooksearchapp.presentation.setting.SettingActivity
import com.example.kakaobooksearchapp.presentation.viewmodel.ThemeViewModel
import com.example.kakaobooksearchapp.ui.theme.ThemeMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle(initialValue = false)
            var currentThemeMode by rememberSaveable { mutableStateOf(ThemeMode.LIGHT) }

            currentThemeMode = when (isDarkMode) {
                true -> ThemeMode.DARK
                false -> ThemeMode.LIGHT
            }

            AppThemeWrapper {
                Scaffold(
                    bottomBar = {
                        MainBottomBar(
                            selectedIndex = 0,
                            bottomNavItems = rememberBottomNavItems(),
                            onClick = {
                                Intent(this, SettingActivity::class.java).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                }.also { startActivity(it) }
                            }
                        )
                    }
                ) { innerPadding ->
                    BookListScreen(
                        innerPadding = innerPadding
                    )
                }
            }

            BackHandler {
                finishAffinity()
            }
        }
    }
}