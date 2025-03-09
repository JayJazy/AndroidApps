package com.example.kakaobooksearchapp.data.module

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.kakaobooksearchapp.data.service.KakaoBookService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

const val BASE_URL = "https://dapi.kakao.com/"
const val API_KEY = "0a345b00134e7ba003e7b97ad3c0d6a6"

private val Context.ThemeModeDataStore by preferencesDataStore(name = "theme_mode_pref")

@Module
@InstallIn(SingletonComponent::class)
object KakaoBookServiceModule{

    @Provides
    fun provideRetrofit(): Retrofit {
        val converterFactory = Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideUserService(retrofit: Retrofit): KakaoBookService{
        return retrofit.create(KakaoBookService::class.java)
    }

    @Provides
    @Singleton
    @Named("ThemeModeDataStore")
    fun providesPreferencesThemeModeDataStore(@ApplicationContext context: Context) = context.ThemeModeDataStore
}