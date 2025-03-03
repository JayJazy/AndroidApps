package com.example.kakaobooksearchapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(

): ViewModel() {

    private val _onClickItem = Channel<String>()
    val onClickItem = _onClickItem.receiveAsFlow()

    fun onItemClick(route: String) {
        viewModelScope.launch {
            _onClickItem.send(route)
        }
    }
}