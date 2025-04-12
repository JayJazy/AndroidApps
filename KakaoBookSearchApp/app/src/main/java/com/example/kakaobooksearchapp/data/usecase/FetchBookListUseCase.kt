package com.example.kakaobooksearchapp.data.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kakaobooksearchapp.data.datasource.room.BookDatabase
import com.example.kakaobooksearchapp.data.datasource.BookRemoteMediator
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.service.KakaoBookService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchBookListUseCase @Inject constructor(
    private val kakaoBookService: KakaoBookService,
    private val bookDatabase: BookDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    fun fetchBookList(
        query: String,
        sort: String,
    ): Flow<PagingData<Document>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            remoteMediator = BookRemoteMediator(
                kakaoBookService = kakaoBookService,
                bookDatabase = bookDatabase,
                query = query,
                sort = sort
            ),
            pagingSourceFactory = {
                bookDatabase.bookDao().pagingSource()
            }
        ).flow
    }
}