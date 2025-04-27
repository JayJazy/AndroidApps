package com.example.kakaobooksearchapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.usecase.FetchBookListUseCase
import com.example.kakaobooksearchapp.presentation.model.BookListState
import com.example.kakaobooksearchapp.presentation.model.BookListUiEffect
import com.example.kakaobooksearchapp.presentation.model.BookSortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val fetchBookListUseCase: FetchBookListUseCase
): BaseViewModel<BookListState, BookListUiEffect>() {

    private val _searchText = MutableStateFlow("kotlin")
    val searchText = _searchText.asStateFlow()

    private val _selectedSortType = MutableStateFlow(BookSortType.Accuracy)
    val selectedSortType = _selectedSortType.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    override fun onError(throwable: Throwable) {
        _isRefreshing.value = false
        _uiState.value = BookListState.Error
        Log.e("ViewModel", "Error : $throwable")
    }

    private var sortedText = "accuracy"

    init {
        _uiState.value = BookListState.Loading
        initData()
    }

    private fun initData(){
        launchSafely {
            _uiState.value = BookListState.Success(
                bookList = fetchBookListUseCase.fetchBookList(
                    query = _searchText.value,
                    sort = sortedText
                ).cachedIn(viewModelScope)
            )
        }
    }

    fun setSearchText(searchText: String) {
        _searchText.value = searchText
    }

    fun searchBookList(searchText: String) {
        launchSafely {
            if (searchText.isEmpty()) {
                _uiEffect.emit(BookListUiEffect.ToastEmptySearchText)
            } else {
                _searchText.value = searchText
                fetchBookList()
            }
        }
    }

    fun requestBookList() {
        _searchText.value = "kotlin"
        _errorDialogState.value = false
        _uiState.value = BookListState.Loading
        initData()
    }

    fun updateBookList(sortText: String) {
        when (sortText) {
            BookSortType.Accuracy.value -> {
                _selectedSortType.value = BookSortType.Accuracy
                sortedText = "accuracy"
            }
            BookSortType.Latest.value -> {
                _selectedSortType.value = BookSortType.Latest
                sortedText = "latest"
            }
        }

        viewModelScope.launch {
            _uiEffect.emit(BookListUiEffect.OnClickBookSort)
            return@launch
        }
        fetchBookList()
    }

    fun onBookClicked(document: Document) {
        launchSafely {
            _uiEffect.emit(BookListUiEffect.OnClickBookDetail(document.isbn))
            return@launchSafely
        }
    }

    fun fetchBookList() {
        val state = _uiState.value
        if (state !is BookListState.Success) {
            _uiState.value = BookListState.Error
            return
        }

        _isRefreshing.value = true

        launchSafely {
            _uiState.update {
                state.copy(
                    bookList = fetchBookListUseCase.fetchBookList(
                        query = _searchText.value,
                        sort = sortedText
                    ).cachedIn(viewModelScope).onStart {
                        _isRefreshing.value = false
                    }
                )
            }
        }
    }
}