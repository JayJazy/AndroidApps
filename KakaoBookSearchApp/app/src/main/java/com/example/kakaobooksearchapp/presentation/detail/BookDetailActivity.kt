package com.example.kakaobooksearchapp.presentation.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.kakaobooksearchapp.presentation.AppThemeWrapper

class BookDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppThemeWrapper {

            }
        }
    }
}