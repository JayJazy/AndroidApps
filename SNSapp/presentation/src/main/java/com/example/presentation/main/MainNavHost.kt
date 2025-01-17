package com.example.presentation.main

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost()
{
    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    title = { Text(stringResource(R.string.app_name)) })
            },
            content = { },
            bottomBar = { }
        )
    }
}