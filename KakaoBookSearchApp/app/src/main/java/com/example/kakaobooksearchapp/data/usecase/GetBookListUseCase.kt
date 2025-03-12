package com.example.kakaobooksearchapp.data.usecase

import com.example.kakaobooksearchapp.data.model.KakaoBookResponse
import com.example.kakaobooksearchapp.data.service.KakaoBookService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val kakaoBookService: KakaoBookService,
) {
    fun getBookList(
        query: String,
        page: Int,
        size: Int
    ): Flow<KakaoBookResponse> = flow {
        val response = kakaoBookService.getBookList(
            query = query,
            page = page,
            size = size
        )
        try{
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            }
        } catch(e : Exception) {
            throw e
        }
    }
}