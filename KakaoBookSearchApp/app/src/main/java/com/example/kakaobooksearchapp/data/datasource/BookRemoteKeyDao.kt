package com.example.kakaobooksearchapp.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookRemoteKeyDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: BookRemoteKey)

    @Query("SELECT * FROM book_remote_key ORDER BY nextPage DESC LIMIT 1")
    suspend fun getNextKey(): BookRemoteKey?

    @Query("DELETE FROM book_remote_key")
    suspend fun deleteRemoteKey()
}