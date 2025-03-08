package com.example.kakaobooksearchapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.presentation.home.component.BookItem
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel
import kotlinx.coroutines.delay

@Composable
fun BookItemListScreen(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    viewModel: BookViewModel = hiltViewModel()
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val bookList by viewModel.bookList.collectAsStateWithLifecycle()

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1000)
            isRefreshing = false
        }
    }

    BookItemListScreen(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = { isRefreshing = true },
        bookList = bookList,
        onItemClick = onItemClick,
        onItemSet = viewModel::setBookDetailItem
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItemListScreen(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    bookList: List<Document>,
    onItemClick: (String) -> Unit,
    onItemSet: (Document) -> Unit
) {
    val state = rememberPullToRefreshState()
    val gridState = rememberLazyGridState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
            .fillMaxSize(),
        state = state,
        indicator = {
            Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = isRefreshing,
            containerColor = MaterialTheme.colorScheme.background,
            color = MaterialTheme.colorScheme.onPrimary,
            state = state
        ) }
    ) {
        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            columns = GridCells.Fixed(2),
            state = gridState,
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            items(
                count = bookList.size,
                key = { index -> bookList[index].isbn }
            ) {
                BookItem(
                    modifier = Modifier,
                    bookData = bookList[it],
                    onBookmarkClick = {},
                    onItemClick = onItemClick,
                    onItemSet = onItemSet
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBookItemListScreen() {
    BookItemListScreen(
        modifier = Modifier,
        isRefreshing = false,
        onRefresh = {},
        bookList = listOf(),
        onItemClick = {},
        onItemSet = {}
    )
}