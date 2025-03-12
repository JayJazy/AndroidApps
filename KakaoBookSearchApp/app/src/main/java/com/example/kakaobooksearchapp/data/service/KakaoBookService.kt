package com.example.kakaobooksearchapp.data.service

import com.example.kakaobooksearchapp.data.model.KakaoBookResponse
import com.example.kakaobooksearchapp.data.module.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoBookService{

    @Headers("Authorization: KakaoAK $API_KEY")
    @GET("v3/search/book")
    suspend fun getBookList(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<KakaoBookResponse>
}