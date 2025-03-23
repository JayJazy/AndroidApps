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

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    var uiState = MutableStateFlow<BookListState>(BookListState.Loading)
        private set

    var uiEffect = MutableSharedFlow<BookListUiEffect>()
        private set

    init {
        uiState.value = BookListState.Loading
        initData()
    }

    private fun initData(){
        viewModelScope.launch {
            try{
                uiState.value = BookListState.Success(
                    bookList = getBookListUseCase.getBookList(
                        query = "kotlin"
                    ).cachedIn(viewModelScope)
                )
            } catch (e: Exception) {
                Log.e("ViewModel", "Error initData: ${e.message}")
                uiState.value = BookListState.Error
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
            uiEffect.emit(BookListUiEffect.OnClickBookDetail)

            uiState.update {
                state.copy(
                    bookDetailItem = document
                )
            }
        }
    }

    fun getBookList(query: String) {
        _isRefreshing.value = true

        val state = uiState.value
        if (state !is BookListState.Success) return

        viewModelScope.launch {
            try {
                uiState.update {
                    state.copy(
                        bookList = getBookListUseCase.getBookList(
                            query = query
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
                uiState.value = BookListState.Error
            }
        }
    }
}