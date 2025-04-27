package com.example.kakaobooksearchapp.presentation.booklist.item

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kakaobooksearchapp.R
import com.example.kakaobooksearchapp.presentation.component.BookSortComponent
import com.example.kakaobooksearchapp.presentation.model.BookSortType

@Composable
fun BookSortItem(
    selectedSortType: BookSortType,
    onSortBoxClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
    ) {
        BookSortComponent(
            sortingText = stringResource(id = R.string.sorting_text1),
            selectedSortType = selectedSortType,
            onSortBoxClick = onSortBoxClick
        )

        Spacer(modifier = Modifier.width(12.dp))

        BookSortComponent(
            sortingText = stringResource(id = R.string.sorting_text2),
            selectedSortType = selectedSortType,
            onSortBoxClick = onSortBoxClick
        )
    }
}