package com.example.kakaobooksearchapp.presentation.model

import com.example.kakaobooksearchapp.data.model.Document

fun dummyDocumentList(): List<Document> {
    val dummyList = List(10) {
        Document(
            authors = listOf(),
            contents = "",
            datetime = "",
            isbn = it.toString(),
            price = 0,
            publisher = "",
            salePrice = 0,
            status = "",
            thumbnail = "",
            title = "",
            translators = listOf(),
            url = ""
        )
    }
    return dummyList
}