package com.example.kakaobooksearchapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.presentation.component.ErrorDialog
import com.example.kakaobooksearchapp.presentation.component.shimmerEffect
import com.example.kakaobooksearchapp.presentation.home.item.emptyBook
import com.example.kakaobooksearchapp.presentation.home.item.existBook
import com.example.kakaobooksearchapp.presentation.home.item.shimmerBook
import com.example.kakaobooksearchapp.presentation.model.BookListState
import com.example.kakaobooksearchapp.presentation.model.BookListUiEffect
import com.example.kakaobooksearchapp.presentation.model.dummyDocument
import com.example.kakaobooksearchapp.presentation.navigtation.model.BookNavItem
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun BookListScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: BookViewModel = hiltViewModel()
) {
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val shimmerBrush = shimmerEffect()
    val shimmerEffectModifier = Modifier
        .background(
            shape = RoundedCornerShape(12.dp),
            brush = shimmerBrush
        )

    LaunchedEffect(viewModel.uiEffect) {
        viewModel.uiEffect.collect { sideEffect ->
            when (sideEffect) {
                is BookListUiEffect.OnClickBookDetail -> {
                    navController.navigate(BookNavItem.BookDetailItem.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }

    when (uiState) {
        is BookListState.Loading -> {
            BookListScreen(
                modifier = modifier,
                isShimmerEffect = true,
                shimmerEffectModifier = shimmerEffectModifier,
                isRefreshing = isRefreshing,
                onRefresh = viewModel::requestBookList,
                bookList = emptyFlow<PagingData<Document>>().collectAsLazyPagingItems(),
                onSetBookDetailItem = {}
            )
        }

        is BookListState.Error -> {
            ErrorDialog(
                requestBookList = viewModel::requestBookList
            )
        }

        is BookListState.Success -> {
            val value = uiState as BookListState.Success

            BookListScreen(
                modifier = modifier,
                isRefreshing = isRefreshing,
                onRefresh = viewModel::requestBookList,
                bookList = value.bookList.collectAsLazyPagingItems(),
                onSetBookDetailItem = viewModel::setBookDetailItem
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    modifier: Modifier = Modifier,
    isShimmerEffect: Boolean = false,
    shimmerEffectModifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    bookList: LazyPagingItems<Document>,
    onSetBookDetailItem: (Document) -> Unit
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
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            columns = GridCells.Fixed(2),
            state = gridState,
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (isShimmerEffect) {
                shimmerBook(
                    modifier = shimmerEffectModifier,
                    bookData = dummyDocument(),
                    onSetBookDetailItem = onSetBookDetailItem
                )
            } else {
                if (bookList.itemCount > 0) {
                    existBook(
                        itemCount = bookList.itemCount,
                        bookList = bookList,
                        onSetBookDetailItem = onSetBookDetailItem
                    )
                }
                else emptyBook()
            }
        }
    }
}