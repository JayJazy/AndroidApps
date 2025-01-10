package com.example.data.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private val Context.UserDataStore by preferencesDataStore(name = "UserDataStore")


@Module
@InstallIn(SingletonComponent::class)
object UserDataStoreModule {

    @Provides
    fun provideUserDataStore(@ApplicationContext context: Context) = context.UserDataStore

}