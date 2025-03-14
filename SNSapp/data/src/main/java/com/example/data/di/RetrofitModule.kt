package com.example.data.di

import com.example.data.retrofit.SNSInterceptor
import com.example.data.retrofit.UserService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit


val SNS_HOST = "http://192.168.0.2:8080"

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    fun provideOkHttpClient(interceptor: SNSInterceptor):OkHttpClient{
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()
    }


    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val converterFactory = Json{ ignoreUnknownKeys = true }.asConverterFactory("application/json; charset=UTF8".toMediaType())
        return Retrofit.Builder()
            .baseUrl("${SNS_HOST}/api/")
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Provides
    fun provideUserService(retrofit:Retrofit):UserService{
        return retrofit.create(UserService::class.java)
    }

}