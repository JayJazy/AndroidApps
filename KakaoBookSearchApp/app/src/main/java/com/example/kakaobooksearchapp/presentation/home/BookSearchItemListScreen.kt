package com.example.kakaobooksearchapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel

@Composable
fun BookSearchItemListScreen(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    viewModel: BookViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("BookSearchItemListScreen")
    }
}