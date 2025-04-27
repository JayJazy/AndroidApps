package com.example.kakaobooksearchapp.presentation.viewmodel

import android.util.Log
import com.example.kakaobooksearchapp.data.usecase.FetchDetailBookUseCase
import com.example.kakaobooksearchapp.presentation.model.DetailBookState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class DetailBookViewModel @Inject constructor(
    private val fetchDetailBookUseCase: FetchDetailBookUseCase
) : BaseViewModel<DetailBookState, Any>() {

    override fun onError(throwable: Throwable) {
        _uiState.value = DetailBookState.Error
        Log.e("ViewModel", "Error : $throwable")
    }

    init {
        _uiState.value = DetailBookState.Loading
    }

    fun fetchBookData(isbn: String) {
        _uiState.value = DetailBookState.Loading
        launchSafely {
            fetchDetailBookUseCase.fetchDetailBook(isbn).collectLatest { document ->
                _uiState.value = DetailBookState.Success(
                    bookDetail = document
                )
            }
        }
    }
}