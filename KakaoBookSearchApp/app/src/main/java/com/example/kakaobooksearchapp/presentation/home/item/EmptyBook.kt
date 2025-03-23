package com.example.kakaobooksearchapp.presentation.home.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.kakaobooksearchapp.R
import kotlinx.coroutines.delay

fun LazyGridScope.emptyBook(){
    item(span = { GridItemSpan(maxLineSpan) }) {

        var isVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(200)
            isVisible = true
        }

        if (isVisible) {
            Column(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.empty_book_list),
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}