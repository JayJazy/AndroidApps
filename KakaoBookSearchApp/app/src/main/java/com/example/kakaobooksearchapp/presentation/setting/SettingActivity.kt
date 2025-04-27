package com.example.kakaobooksearchapp.presentation.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kakaobooksearchapp.presentation.AppThemeWrapper
import com.example.kakaobooksearchapp.presentation.MainActivity
import com.example.kakaobooksearchapp.presentation.component.MainBottomBar
import com.example.kakaobooksearchapp.presentation.model.rememberBottomNavItems
import com.example.kakaobooksearchapp.presentation.setting.screen.SettingScreen
import com.example.kakaobooksearchapp.presentation.viewmodel.ThemeViewModel
import com.example.kakaobooksearchapp.ui.theme.ThemeMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : ComponentActivity() {

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
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MainBottomBar(
                            selectedIndex = 1,
                            bottomNavItems = rememberBottomNavItems(),
                            onClick = {
                                Intent(this, MainActivity::class.java).apply {
                                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                }.also { startActivity(it) }
                            }
                        )
                    }
                ) { innerPadding ->
                    SettingScreen(
                        currentThemeMode = currentThemeMode,
                        onThemeModeChange = themeViewModel::setDarkMode,
                        modifier = Modifier
                            .padding(innerPadding.calculateBottomPadding())
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}
