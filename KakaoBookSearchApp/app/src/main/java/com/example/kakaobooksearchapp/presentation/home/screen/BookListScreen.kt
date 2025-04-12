package com.example.kakaobooksearchapp.presentation.home.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kakaobooksearchapp.R
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.model.Document.Companion.dummyBook
import com.example.kakaobooksearchapp.presentation.component.BookLazyVerticalGridComponent
import com.example.kakaobooksearchapp.presentation.component.ErrorDialog
import com.example.kakaobooksearchapp.presentation.home.item.BookSortItem
import com.example.kakaobooksearchapp.presentation.home.item.emptyBookItem
import com.example.kakaobooksearchapp.presentation.home.item.errorBookItem
import com.example.kakaobooksearchapp.presentation.home.item.existBookItem
import com.example.kakaobooksearchapp.presentation.home.item.shimmerBookItem
import com.example.kakaobooksearchapp.presentation.model.BookListState
import com.example.kakaobooksearchapp.presentation.model.BookListUiEffect
import com.example.kakaobooksearchapp.presentation.model.BookSortType
import com.example.kakaobooksearchapp.presentation.navigtation.model.BookNavItem
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedSortType by viewModel.selectedSortType.collectAsStateWithLifecycle()
    val state = rememberPullToRefreshState()
    val gridState = rememberLazyGridState()

    LaunchedEffect (viewModel.uiEffect) {
        viewModel.uiEffect.collect { sideEffect ->
            when (sideEffect) {
                is BookListUiEffect.OnClickBookDetail -> {
                    navController.navigate(BookNavItem.BookDetailItem.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }

                is BookListUiEffect.ToastEmptySearchText -> {
                    Toast.makeText(context, R.string.empty_search_text, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    when (uiState) {
        is BookListState.Error -> {
            ErrorDialog(requestBookList = viewModel::fetchBookList)
        }

        is BookListState.Loading -> {
            BookListContent(
                isRefreshing = isRefreshing,
                state = state,
                gridState = gridState,
                selectedSortType = selectedSortType,
                bookList = emptyFlow<PagingData<Document>>().collectAsLazyPagingItems(),
                onRefresh = viewModel::fetchBookList,
                onSortBoxClick = {},
                onBookClick = {},
                modifier = modifier,
            )
        }

        is BookListState.Success -> {
            val value = uiState as BookListState.Success

            BookListContent(
                isRefreshing = isRefreshing,
                state = state,
                gridState = gridState,
                selectedSortType = selectedSortType,
                bookList = value.bookList.collectAsLazyPagingItems(),
                onRefresh = viewModel::fetchBookList,
                onSortBoxClick = viewModel::updateBookList,
                onBookClick = viewModel::updateSelectedBook,
                modifier = modifier,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListContent(
    isRefreshing: Boolean,
    state: PullToRefreshState,
    gridState: LazyGridState,
    selectedSortType: BookSortType,
    bookList: LazyPagingItems<Document>,
    onRefresh: () -> Unit,
    onSortBoxClick: (String) -> Unit,
    onBookClick: (Document) -> Unit,
    modifier: Modifier = Modifier
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier
            .fillMaxSize(),
        state = state,
        indicator = {
            Indicator(
                isRefreshing = isRefreshing,
                modifier = Modifier.align(Alignment.TopCenter),
                containerColor = MaterialTheme.colorScheme.background,
                color = MaterialTheme.colorScheme.onPrimary,
                state = state
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BookSortItem(
                selectedSortType = selectedSortType,
                onSortBoxClick = onSortBoxClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            when {
                bookList.loadState.refresh is LoadState.Loading -> {
                    BookLazyVerticalGridComponent(state = gridState) {
                        shimmerBookItem(
                            bookDetail = dummyBook(),
                            onBookClick = {}
                        )
                    }
                }

                bookList.loadState.refresh is LoadState.NotLoading && bookList.itemCount > 0 -> {
                    BookLazyVerticalGridComponent(state = gridState) {
                        existBookItem(
                            itemCount = bookList.itemCount,
                            bookList = bookList,
                            onBookClick = onBookClick
                        )
                    }
                }

                bookList.loadState.refresh is LoadState.NotLoading && bookList.itemCount == 0 -> {
                    BookLazyVerticalGridComponent(state = gridState) {
                        emptyBookItem()
                    }
                }

                else -> BookLazyVerticalGridComponent(state = gridState) {
                    errorBookItem(onRefresh)
                }
            }
        }
    }
}