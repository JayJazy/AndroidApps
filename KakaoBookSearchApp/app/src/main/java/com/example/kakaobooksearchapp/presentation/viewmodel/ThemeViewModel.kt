package com.example.kakaobooksearchapp.presentation.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

val DARK_MODE = booleanPreferencesKey("dark_mode")

@HiltViewModel
class ThemeViewModel @Inject constructor(
    @Named("ThemeModeDataStore") private val dataStore: DataStore<Preferences>
): ViewModel() {

    val isDarkMode: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DARK_MODE] ?: false
        }

    fun setDarkMode(isDark: Boolean) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[DARK_MODE] = isDark
            }
        }
    }
}