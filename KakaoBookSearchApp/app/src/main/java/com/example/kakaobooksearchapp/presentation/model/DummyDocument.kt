package com.example.kakaobooksearchapp.presentation.model

import com.example.kakaobooksearchapp.data.model.Document

fun dummyBook(): Document {
    return Document(
        authors = listOf(),
        contents = "",
        datetime = "",
        isbn = "0",
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