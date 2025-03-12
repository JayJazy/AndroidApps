package com.example.kakaobooksearchapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.usecase.GetBookListUseCase
import com.example.kakaobooksearchapp.presentation.model.BookListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase
): ViewModel() {

    private val _onClickItem = Channel<String>()
    val onClickItem = _onClickItem.receiveAsFlow()

    private val _bookDetailItem = MutableStateFlow<Document?>(null)
    val bookDetailItem = _bookDetailItem.asStateFlow()

    private val _searchText = MutableStateFlow("")

    var uiState = MutableStateFlow<BookListState>(BookListState.Loading)
        private set

    init {
        uiState.value = BookListState.Loading

        viewModelScope.launch {
            getBookListUseCase.getBookList(
                query = "kotlin",
                page = 1,
                size = 1
            ).catch {
                uiState.value = BookListState.Error
            }.collectLatest { response ->
                uiState.value = BookListState.Success(bookList = response.documents)
            }
        }
    }

    fun onItemClick(route: String) {
        viewModelScope.launch {
            _onClickItem.send(route)
        }
    }

    fun setBookDetailItem(document: Document) {
        _bookDetailItem.value = document
    }

    fun setSearchText(searchText: String) {
        _searchText.value = searchText
    }

    fun requestBookList() {
        getBookList(
            query = _searchText.value.ifEmpty { "kotlin" }
        )
    }

    fun getBookList(
        query: String,
        page: Int = 2,
        size: Int = 10
    ) {
        val state = uiState.value
        if (state !is BookListState.Success) return

        viewModelScope.launch {
            getBookListUseCase.getBookList(
                query = query,
                page = page,
                size = size
            ).catch {
                uiState.value = BookListState.Error
            }.collectLatest { response ->
                uiState.update {
                    state.copy(
                        bookList = response.documents
                    )
                }
            }
        }
    }
}