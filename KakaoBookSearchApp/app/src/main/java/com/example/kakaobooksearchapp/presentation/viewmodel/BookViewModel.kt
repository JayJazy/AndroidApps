package com.example.kakaobooksearchapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.usecase.GetBookListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase
): ViewModel() {

    init {
        getAllBookList(query = "kotlin")
    }

    private val _onClickItem = Channel<String>()
    val onClickItem = _onClickItem.receiveAsFlow()

    private val _bookList = MutableStateFlow<List<Document>>(emptyList())
    val bookList = _bookList.asStateFlow()

    private val _bookDetailItem = MutableStateFlow<Document?>(null)
    val bookDetailItem = _bookDetailItem.asStateFlow()

    private val _error = MutableSharedFlow<Throwable>()
    val error = _error.asSharedFlow()

    fun onItemClick(route: String) {
        viewModelScope.launch {
            _onClickItem.send(route)
        }
    }

    fun setBookDetailItem(document: Document){
        _bookDetailItem.value = document
    }

    fun getAllBookList(
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
                _error.emit(it)
            }.collectLatest {
                _bookList.value = it.documents
            }
        }
    }
}