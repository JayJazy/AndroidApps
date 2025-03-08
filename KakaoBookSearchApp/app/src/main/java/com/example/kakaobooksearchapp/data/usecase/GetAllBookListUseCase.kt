package com.example.kakaobooksearchapp.data.usecase

import com.example.kakaobooksearchapp.data.model.KakaoBookResponse
import com.example.kakaobooksearchapp.data.service.KakaoBookService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllBookListUseCase @Inject constructor(
    private val kakaoBookService: KakaoBookService,
) {
    fun getAllBookList(
        query: String,
        page: Int,
        size: Int
    ): Flow<KakaoBookResponse> = flow {
        val response = kakaoBookService.getAllBookList(
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