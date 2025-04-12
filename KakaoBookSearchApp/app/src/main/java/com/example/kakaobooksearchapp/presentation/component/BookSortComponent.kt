package com.example.kakaobooksearchapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kakaobooksearchapp.R
import com.example.kakaobooksearchapp.presentation.model.BookSortType

@Composable
fun BookSortComponent(
    sortingText: String,
    selectedSortType: BookSortType,
    onSortBoxClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelect = selectedSortType.value == sortingText

    Box(
        modifier = modifier
            .background(
                color = if (isSelect) colorResource(id = R.color.selected_sort_bg)
                else colorResource(id = R.color.unselected_sort_bg),
                shape = RoundedCornerShape(16.dp)
            )
            .wrapContentWidth()
            .height(36.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onSortBoxClick(sortingText)
            }
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = sortingText,
            style = MaterialTheme.typography.displayMedium.copy(
                color = if (isSelect) colorResource(id = R.color.selected_sort_text)
                else colorResource(id = R.color.unselected_sort_text)
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBookSortComponent(){
    BookSortComponent(
        selectedSortType = BookSortType.Accuracy,
        sortingText = "정확도 순",
        onSortBoxClick = {}
    )
}