package com.example.kakaobooksearchapp.data.usecase

import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.service.KakaoBookService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchDetailBookUseCase @Inject constructor(
    private val kakaoBookService: KakaoBookService
) {
    fun fetchDetailBook(
        isbn: String
    ): Flow<Document> = flow {
        val isbnList = isbn.split(" ")

        for (singleIsbn in isbnList) {
            val cleanSingleIsbn = singleIsbn.replace("-", "").trim()

            runCatching {
                kakaoBookService.fetchDetailBook(singleIsbn)
            }.onSuccess { response ->
                if (response.isSuccessful && response.body()?.documents?.isNotEmpty() == true) {
                    emit(response.body()!!.documents[0])
                    return@flow
                }

                runCatching {
                    kakaoBookService.fetchDetailBook(cleanSingleIsbn)
                }.onSuccess { cleanResponse ->
                    if (cleanResponse.isSuccessful && cleanResponse.body()?.documents?.isNotEmpty() == true) {
                        emit(cleanResponse.body()!!.documents[0])
                        return@flow
                    }
                }
            }
        }

        throw NoSuchElementException("책 정보를 찾을 수 없습니다: $isbn")
    }
}