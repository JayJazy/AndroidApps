package com.example.kakaobooksearchapp.data.module

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.kakaobooksearchapp.data.datasource.room.BookDatabase
import com.example.kakaobooksearchapp.data.service.KakaoBookService
import com.facebook.shimmer.BuildConfig
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
    fun provideKakaoBookService(retrofit: Retrofit): KakaoBookService{
        return retrofit.create(KakaoBookService::class.java)
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): BookDatabase {
        return Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            "book_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    @Named("ThemeModeDataStore")
    fun providesPreferencesThemeModeDataStore(@ApplicationContext context: Context) = context.ThemeModeDataStore
}