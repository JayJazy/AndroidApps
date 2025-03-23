package com.example.kakaobooksearchapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "document")
@Serializable
data class Document(
    @PrimaryKey
    val isbn: String,

    val authors: List<String>,
    val contents: String,
    val datetime: String,
    val price: Int,
    val publisher: String,

    @ColumnInfo(name = "sale_price")
    @SerialName("sale_price")
    val salePrice: Int,

    val status: String,
    val thumbnail: String,
    val title: String,
    val translators: List<String>,
    val url: String
)