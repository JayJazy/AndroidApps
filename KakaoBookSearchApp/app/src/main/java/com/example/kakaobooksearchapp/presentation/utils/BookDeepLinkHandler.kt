package com.example.kakaobooksearchapp.presentation.utils

import android.content.Context
import android.content.Intent
import com.airbnb.deeplinkdispatch.DeepLink
import com.airbnb.deeplinkdispatch.handler.DeepLinkHandler
import com.example.kakaobooksearchapp.presentation.MainActivity
import com.example.kakaobooksearchapp.presentation.detail.DetailBookActivity
import com.example.kakaobooksearchapp.presentation.model.BookInfo

@DeepLink("kakaobooksearchapp://book/{isbn}")
object BookDeepLinkHandler : DeepLinkHandler<BookInfo> {

    override fun handleDeepLink(
        context: Context,
        deepLinkArgs: BookInfo
    ) {
        Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }.also { context.startActivity(it) }

        Intent(context, DetailBookActivity::class.java).apply {
            putExtra("isbn", deepLinkArgs.isbn)
        }.also { context.startActivity(it) }
    }
}
