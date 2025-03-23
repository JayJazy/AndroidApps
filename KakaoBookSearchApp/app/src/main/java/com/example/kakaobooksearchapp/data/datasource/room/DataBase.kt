package com.example.kakaobooksearchapp.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kakaobooksearchapp.data.datasource.BookDao
import com.example.kakaobooksearchapp.data.datasource.BookRemoteKey
import com.example.kakaobooksearchapp.data.datasource.BookRemoteKeyDao
import com.example.kakaobooksearchapp.data.model.Document

@Database(
    entities = [Document::class, BookRemoteKey::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListTypeConverter::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun bookRemoteKeyDao(): BookRemoteKeyDao
}