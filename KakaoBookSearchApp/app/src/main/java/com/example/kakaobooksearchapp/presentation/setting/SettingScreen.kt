package com.example.kakaobooksearchapp.presentation.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kakaobooksearchapp.R
import com.example.kakaobooksearchapp.ui.theme.ThemeMode

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    currentThemeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit
) {
    val radioOptions = listOf(
        ThemeMode.LIGHT to stringResource(id = R.string.light_theme),
        ThemeMode.DARK to stringResource(id = R.string.dark_theme)
    )

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        radioOptions.forEach{ (mode, label) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = currentThemeMode == mode,
                        onClick = { onThemeModeChange(mode) }
                    )
                    .padding(horizontal = 100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = currentThemeMode == mode,
                    onClick = { onThemeModeChange(mode) },
                    colors = RadioButtonColors(
                        selectedColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedColor = MaterialTheme.colorScheme.surface,
                        disabledSelectedColor = MaterialTheme.colorScheme.onPrimary,
                        disabledUnselectedColor = MaterialTheme.colorScheme.surface
                    )
                )
                Text(
                    modifier = modifier
                        .fillMaxWidth(),
                    text = label,
                    color = if(currentThemeMode  == mode) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingScreen(){
    SettingScreen(
        modifier = Modifier,
        currentThemeMode = ThemeMode.LIGHT,
        onThemeModeChange = { }
    )
}