package com.example.kakaobooksearchapp.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kakaobooksearchapp.data.model.Document

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(documentList: List<Document>)

    @Query("SELECT * FROM document")
    fun pagingSource(): PagingSource<Int, Document>

    @Query("DELETE FROM document")
    suspend fun clearAllDocuments()
}