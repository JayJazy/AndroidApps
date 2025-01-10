package com.example.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserDataStore @Inject constructor(
   private val userDataStore : DataStore<Preferences>
) {
    companion object{
        private val USER_KEY = stringPreferencesKey("USER_KEY")
    }


    suspend fun setToken(token:String){
        userDataStore.edit { preferences ->
            preferences[USER_KEY] = token
        }
    }

    suspend fun getToken():String? {
        return userDataStore.data.map { preferences ->
            preferences[USER_KEY]
        }.firstOrNull()
    }


    suspend fun clearToken() {
        userDataStore.edit {
            it.clear()
        }
    }

}