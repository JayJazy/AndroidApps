package com.example.kakaobooksearchapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S, E>: ViewModel() {

    protected val _uiState = MutableStateFlow<S?>(null)
    val uiState = _uiState.asStateFlow()

    protected val _uiEffect = MutableSharedFlow<E>()
    val uiEffect = _uiEffect.asSharedFlow()

    protected val _errorDialogState = MutableStateFlow(false)
    val errorDialogState = _errorDialogState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _errorDialogState.value = true
        onError(throwable)
    }

    protected abstract fun onError(throwable: Throwable)

    protected fun launchSafely(
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(exceptionHandler) {
            block()
        }
    }
}