package com.example.kakaobooksearchapp.data.service

import com.example.kakaobooksearchapp.BuildConfig
import com.example.kakaobooksearchapp.data.model.KakaoBookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val api_key = BuildConfig.API_KEY

interface KakaoBookService{

    @Headers("Authorization: KakaoAK $api_key")
    @GET("v3/search/book")
    suspend fun fetchBookList(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<KakaoBookResponse>
}