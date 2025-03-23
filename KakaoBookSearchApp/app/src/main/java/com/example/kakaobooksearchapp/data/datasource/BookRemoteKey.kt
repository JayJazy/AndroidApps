package com.example.kakaobooksearchapp.data.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_remote_key")
data class BookRemoteKey(
    @PrimaryKey
    val nextPage: Int,
)