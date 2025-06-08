package com.example.kakaobooksearchapp.presentation.model

import com.airbnb.deeplinkdispatch.handler.DeepLinkParamType
import com.airbnb.deeplinkdispatch.handler.DeeplinkParam

data class BookInfo(
    @DeeplinkParam("isbn", DeepLinkParamType.Path)
    val isbn: String
)