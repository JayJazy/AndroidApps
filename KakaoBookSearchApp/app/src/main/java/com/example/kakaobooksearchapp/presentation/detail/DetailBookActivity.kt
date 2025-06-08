package com.example.kakaobooksearchapp.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kakaobooksearchapp.presentation.AppThemeWrapper
import com.example.kakaobooksearchapp.presentation.component.DetailBookTopBar
import com.example.kakaobooksearchapp.presentation.detail.screen.BookDetailScreen
import com.example.kakaobooksearchapp.presentation.viewmodel.DetailBookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isbn = intent.getStringExtra("isbn") ?: "89969913419788996991342"
        Log.d("dudtjr", isbn)
        enableEdgeToEdge()
        setContent {
            AppThemeWrapper {
                val viewModel: DetailBookViewModel = hiltViewModel()

                LaunchedEffect(Unit) {
                    viewModel.fetchBookData(isbn)
                }

                Scaffold(
                    topBar = {
                        DetailBookTopBar(
                            onBackClick = { finish() }
                        )
                    }
                ) { innerPadding ->
                    BookDetailScreen(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(20.dp)
                    )
                }
            }
        }
    }
}