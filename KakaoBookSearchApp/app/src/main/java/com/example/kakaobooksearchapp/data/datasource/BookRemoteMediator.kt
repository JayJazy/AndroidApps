package com.example.kakaobooksearchapp.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.datasource.room.BookDatabase
import com.example.kakaobooksearchapp.data.service.KakaoBookService

@OptIn(ExperimentalPagingApi::class)
class BookRemoteMediator(
    private val kakaoBookService: KakaoBookService,
    private val bookDatabase: BookDatabase,
    private val query: String,
    private val sort: String
): RemoteMediator<Int, Document>() {

    private val dao = bookDatabase.bookDao()
    private val keyDao = bookDatabase.bookRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Document>,
    ): MediatorResult {
        val remoteKey = when(loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> keyDao.getNextKey()
        }

        try {
            val page = remoteKey?.nextPage ?: 0
            val response = kakaoBookService.getBookList(
                query = query,
                sort = sort,
                page = page,
                size = state.config.pageSize
            )

            val documents = response.body()?.documents ?: emptyList()

            return if (response.isSuccessful) {
                bookDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        dao.clearAllDocuments()
                        keyDao.deleteRemoteKey()
                    }
                    keyDao.insertOrReplace(BookRemoteKey(nextPage = page +1))
                    dao.insertAll(documents)
                }
                MediatorResult.Success(documents.size != state.config.pageSize)
            } else {
                MediatorResult.Error(Exception("API 호출 실패: ${response.code()}"))
            }
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}