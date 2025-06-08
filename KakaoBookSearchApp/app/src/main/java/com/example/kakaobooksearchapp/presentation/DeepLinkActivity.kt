package com.example.kakaobooksearchapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.example.kakaobooksearchapp.presentation.utils.AppDeepLinkModule
import com.example.kakaobooksearchapp.presentation.utils.AppDeepLinkModuleRegistry


@DeepLinkHandler(*[AppDeepLinkModule::class])
class DeepLinkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DeepLinkDelegate 생성
        val deepLinkDelegate = DeepLinkDelegate(
            AppDeepLinkModuleRegistry()
        )

        deepLinkDelegate.dispatchFrom(this)

        finish()
    }
}