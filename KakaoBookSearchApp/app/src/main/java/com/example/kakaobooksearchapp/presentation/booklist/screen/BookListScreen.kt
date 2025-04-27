package com.example.kakaobooksearchapp.presentation.booklist.screen

import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kakaobooksearchapp.presentation.booklist.content.BookListContent
import com.example.kakaobooksearchapp.presentation.component.SearchTopBar
import com.example.kakaobooksearchapp.presentation.detail.DetailBookActivity
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel

@Composable
fun BookListScreen(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()

    Scaffold (
        topBar = {
            SearchTopBar(
                searchText = searchText,
                onSearchTextChange = viewModel::setSearchText,
                onSearchClick = viewModel::searchBookList,
            )
        }
    ) { topPadding ->
        BookListContent(
            viewModel = viewModel,
            onClick = { isbn ->
                Intent(context, DetailBookActivity::class.java).apply {
                    putExtra("isbn", isbn)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }.also { context.startActivity(it) }

            },
            modifier = modifier
                .padding(top = topPadding.calculateTopPadding() + 10.dp)
                .fillMaxSize()
        )
    }
}