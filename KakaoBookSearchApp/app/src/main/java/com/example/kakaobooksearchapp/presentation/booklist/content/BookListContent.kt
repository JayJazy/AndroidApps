package com.example.kakaobooksearchapp.presentation.booklist.content

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kakaobooksearchapp.R
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.model.Document.Companion.dummyBook
import com.example.kakaobooksearchapp.presentation.component.BookLazyVerticalGridComponent
import com.example.kakaobooksearchapp.presentation.component.ErrorDialog
import com.example.kakaobooksearchapp.presentation.booklist.item.BookSortItem
import com.example.kakaobooksearchapp.presentation.booklist.item.emptyBookItem
import com.example.kakaobooksearchapp.presentation.booklist.item.errorBookItem
import com.example.kakaobooksearchapp.presentation.booklist.item.existBookItem
import com.example.kakaobooksearchapp.presentation.booklist.item.shimmerBookItem
import com.example.kakaobooksearchapp.presentation.model.BookListState
import com.example.kakaobooksearchapp.presentation.model.BookListUiEffect
import com.example.kakaobooksearchapp.presentation.model.BookSortType
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListContent(
    viewModel: BookViewModel,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedSortType by viewModel.selectedSortType.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state = rememberPullToRefreshState()

    var scrollIndex by rememberSaveable { mutableIntStateOf(0) }
    var scrollOffset by rememberSaveable { mutableIntStateOf(0) }
    val gridState = rememberSaveable(saver = LazyGridState.Saver) { LazyGridState() }

    LifecycleResumeEffect(scrollOffset) {
        scope.launch {
            gridState.scrollToItem(scrollIndex, scrollOffset)
        }

        onPauseOrDispose {
            scrollIndex = gridState.firstVisibleItemIndex
            scrollOffset = gridState.firstVisibleItemScrollOffset
        }
    }

    LaunchedEffect (viewModel.uiEffect) {
        viewModel.uiEffect.collect { sideEffect ->
            when (sideEffect) {
                is BookListUiEffect.OnClickBookDetail -> {
                    onClick(sideEffect.isbn)
                }

                is BookListUiEffect.ToastEmptySearchText -> {
                    Toast.makeText(context, R.string.empty_search_text, Toast.LENGTH_SHORT).show()
                }

                is BookListUiEffect.OnClickBookSort -> {
                    gridState.animateScrollToItem(0, 0)
                }
            }
        }
    }

    when (uiState) {
        is BookListState.Loading -> {
            BookListContent(
                isRefreshing = isRefreshing,
                state = state,
                gridState = gridState,
                selectedSortType = selectedSortType,
                bookList = emptyFlow<PagingData<Document>>().collectAsLazyPagingItems(),
                onRefresh = viewModel::fetchBookList,
                onRequestClick = viewModel::requestBookList,
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
                onRequestClick = viewModel::requestBookList,
                onSortBoxClick = viewModel::updateBookList,
                onBookClick = viewModel::onBookClicked,
                modifier = modifier,
            )
        }

        else -> {
            ErrorDialog(onRequestClick = viewModel::requestBookList)
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
    onRequestClick: () -> Unit,
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

            when (bookList.loadState.refresh) {
                is LoadState.Loading -> {
                    BookLazyVerticalGridComponent(state = rememberLazyGridState()) {
                        shimmerBookItem(
                            bookDetail = dummyBook(),
                            onBookClick = {}
                        )
                    }
                }

                is LoadState.NotLoading -> {
                    BookLazyVerticalGridComponent(state = gridState) {
                        when {
                            bookList.itemCount > 0 -> {
                                existBookItem(
                                    itemCount = bookList.itemCount,
                                    bookList = bookList,
                                    onBookClick = onBookClick
                                )
                            }
                            bookList.loadState.append is LoadState.NotLoading && bookList.itemCount == 0 -> {
                                emptyBookItem()
                            }
                        }
                    }
                }

                else -> {
                    BookLazyVerticalGridComponent(state = gridState) {
                        errorBookItem(onRequestClick)
                    }
                }
            }
        }
    }
}