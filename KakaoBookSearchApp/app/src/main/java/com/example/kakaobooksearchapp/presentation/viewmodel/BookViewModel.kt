package com.example.kakaobooksearchapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.usecase.GetBookListUseCase
import com.example.kakaobooksearchapp.presentation.model.BookListState
import com.example.kakaobooksearchapp.presentation.model.BookListUiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _uiState = MutableStateFlow<BookListState>(BookListState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<BookListUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        _uiState.value = BookListState.Loading
        initData()
    }

    private fun initData(){
        viewModelScope.launch {
            try{
                _uiState.value = BookListState.Success(
                    bookList = getBookListUseCase.getBookList(
                        query = "kotlin"
                    ).cachedIn(viewModelScope)
                )
            } catch (e: Exception) {
                Log.e("ViewModel", "Error initData: ${e.message}")
                _uiState.value = BookListState.Error
            }
        }
    }

    fun setSearchText(searchText: String) {
        _searchText.value = searchText
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

        viewModelScope.launch {
            _uiEffect.emit(BookListUiEffect.OnClickBookDetail)

            _uiState.update {
                state.copy(
                    bookDetail = document
                )
            }
        }
    }

    fun fetchBookList() {
        _isRefreshing.value = true

        val state = _uiState.value
        if (state !is BookListState.Success) return

        viewModelScope.launch {
            try {
                _uiState.update {
                    state.copy(
                        bookList = getBookListUseCase.getBookList(
                            query = _searchText.value
                        ).cachedIn(viewModelScope).catch {
                            _isRefreshing.value = false
                            throw it
                        }.onStart {
                            _isRefreshing.value = false
                        }
                    )
                }
            } catch(e: Exception) {
                Log.e("ViewModel", "Error getBookList: ${e.message}")
                _isRefreshing.value = false
                _uiState.value = BookListState.Error
            }
        }
    }
}