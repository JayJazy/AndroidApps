package com.example.kakaobooksearchapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.usecase.GetBookListUseCase
import com.example.kakaobooksearchapp.presentation.model.BookListState
import com.example.kakaobooksearchapp.presentation.model.BookListUiEffect
import com.example.kakaobooksearchapp.presentation.model.BookSortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase
): ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _selectedSortType = MutableStateFlow(BookSortType.Accuracy)
    val selectedSortType = _selectedSortType.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _uiState = MutableStateFlow<BookListState>(BookListState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<BookListUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
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
        viewModelScope.launch(exceptionHandler) {
            _uiState.value = BookListState.Success(
                bookList = getBookListUseCase.getBookList(
                    query = "kotlin",
                    sort = sortedText
                ).cachedIn(viewModelScope)
            )
        }
    }

    fun setSearchText(searchText: String) {
        _searchText.value = searchText
    }

    fun updateBookList(sortText: String) {
        when (sortText) {
            BookSortType.Accuracy.value -> sortedText = "accuracy"
            BookSortType.Latest.value -> sortedText = "latest"
        }
        fetchBookList()
    }

    fun searchBookList(searchText: String) {
        viewModelScope.launch {
            if (searchText.isEmpty()) {
                _uiEffect.emit(BookListUiEffect.ToastEmptySearchText)
            } else {
                _searchText.value = searchText
                fetchBookList()
            }
        }
    }

    fun updateSelectedBook(document: Document) {
        val state = uiState.value
        if (state !is BookListState.Success) return

        viewModelScope.launch(exceptionHandler) {
            _uiEffect.emit(BookListUiEffect.OnClickBookDetail)

            _uiState.update {
                state.copy(
                    bookDetail = document
                )
            }
        }
    }

    fun fetchBookList() {
        val state = _uiState.value
        if (state !is BookListState.Success) return

        _isRefreshing.value = true

        viewModelScope.launch(exceptionHandler) {
            _uiState.update {
                state.copy(
                    bookList = getBookListUseCase.getBookList(
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