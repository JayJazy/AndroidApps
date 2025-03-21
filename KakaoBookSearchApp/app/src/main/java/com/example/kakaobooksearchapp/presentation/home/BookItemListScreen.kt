package com.example.kakaobooksearchapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.kakaobooksearchapp.presentation.component.BookItem
import com.example.kakaobooksearchapp.presentation.component.ErrorDialog
import com.example.kakaobooksearchapp.presentation.component.shimmerEffect
import com.example.kakaobooksearchapp.presentation.model.BookListState
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel
import kotlinx.coroutines.delay

@Composable
fun BookItemListScreen(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    viewModel: BookViewModel = hiltViewModel()
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1000)
            isRefreshing = false
        }
    }

    when (uiState) {
        is BookListState.Loading -> {
            val shimmerBrush = shimmerEffect()
            val shimmerEffect = Modifier
                .background(
                    shape = RoundedCornerShape(12.dp),
                    brush = shimmerBrush
                )
            val dummyList = List(10) {
                Document(
                    authors = listOf(),
                    contents = "",
                    datetime = "",
                    isbn = it.toString(),
                    price = 0,
                    publisher = "",
                    salePrice = 0,
                    status = "",
                    thumbnail = "",
                    title = "",
                    translators = listOf(),
                    url = ""
                )
            }

            BookItemListScreen(
                modifier = modifier,
                isShimmerEffect = true,
                shimmerEffectModifier = shimmerEffect,
                isRefreshing = isRefreshing,
                onRefresh = { isRefreshing = true },
                bookList = dummyList,
                onItemClick = onItemClick,
                onItemSet = viewModel::setBookDetailItem
            )
        }

        is BookListState.Error -> {
            ErrorDialog(
                requestBookList = viewModel::requestBookList
            )
        }

        is BookListState.Success -> {
            val value = uiState as BookListState.Success

            BookItemListScreen(
                modifier = modifier,
                isShimmerEffect = false,
                shimmerEffectModifier = modifier,
                isRefreshing = isRefreshing,
                onRefresh = { isRefreshing = true },
                bookList = value.bookList,
                onItemClick = onItemClick,
                onItemSet = viewModel::setBookDetailItem
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItemListScreen(
    modifier: Modifier = Modifier,
    isShimmerEffect: Boolean,
    shimmerEffectModifier: Modifier,
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
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            items(
                count = bookList.size,
                key = { index -> bookList[index].isbn }
            ) {
                BookItem(
                    modifier = if (isShimmerEffect) shimmerEffectModifier else modifier,
                    isShimmerEffect = isShimmerEffect,
                    bookData = bookList[it],
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
        isShimmerEffect = true,
        shimmerEffectModifier = Modifier,
        isRefreshing = false,
        onRefresh = {},
        bookList = listOf(),
        onItemClick = {},
        onItemSet = {}
    )
}