package com.example.kakaobooksearchapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class KakaoBookResponse(
    val meta: Meta,
    val documents: List<Document>
)