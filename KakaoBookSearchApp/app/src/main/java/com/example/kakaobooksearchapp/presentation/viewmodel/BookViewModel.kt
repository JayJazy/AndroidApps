package com.example.kakaobooksearchapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.usecase.GetBookListUseCase
import com.example.kakaobooksearchapp.presentation.model.BookListState
import com.example.kakaobooksearchapp.presentation.model.BookListUiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase
): ViewModel() {

    private val _searchText = MutableStateFlow("")

    var uiState = MutableStateFlow<BookListState>(BookListState.Loading)
        private set

    var uiEffect = MutableSharedFlow<BookListUiEffect>()
        private set

    init {
        viewModelScope.launch {
            getBookListUseCase.getBookList(
                query = "kotlin",
                page = 1,
                size = 10
            ).catch {
                uiState.value = BookListState.Error
            }.onStart {
                uiState.value = BookListState.Loading
            }.collectLatest { response ->
                uiState.value = BookListState.Success(bookList = response.documents)
            }
        }
    }

    fun setSearchText(searchText: String) {
        _searchText.value = searchText
    }

    fun requestBookList() {
        getBookList(
            query = _searchText.value.ifEmpty { "kotlin" }
        )
    }

    fun setBookDetailItem(document: Document) {
        val state = uiState.value
        if (state !is BookListState.Success) return

        viewModelScope.launch {
            uiState.update {
                state.copy(
                    bookDetailItem = document
                )
            }
            uiEffect.emit(BookListUiEffect.OnClickBookDetail(document))
        }
    }

    fun getBookList(
        query: String,
        page: Int = 2,
        size: Int = 10
    ) {
        viewModelScope.launch {
            getBookListUseCase.getBookList(
                query = query,
                page = page,
                size = size
            ).catch {
                uiState.value = BookListState.Error
            }.onStart {
                uiState.value = BookListState.Loading
            }.collectLatest { response ->
                val state = uiState.value

                if (state is BookListState.Success) {
                    uiState.update {
                        state.copy(
                            bookList = response.documents
                        )
                    }
                } else {
                    uiState.value = BookListState.Success(
                        bookList = response.documents
                    )
                }
            }
        }
    }
}