package com.example.kakaobooksearchapp.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kakaobooksearchapp.R

@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    model: String? = null,
    title: String = "제목",
    author: String = "저자",
    isBookmarked: Boolean = false,
    onBookmarkClick: () -> Unit = {},
    onItemClick: (String) -> Unit,
) {
    var isBookmark by remember { mutableStateOf(isBookmarked) }
    Column(
        modifier = modifier
            .clickable { onItemClick("BookDetailItem") }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(12.dp)
                ),
        ) {
            AsyncImage(
                modifier = modifier
                    .align(alignment = Alignment.Center),
                model = model,
                placeholder = painterResource(id = R.drawable.loading_book),
                error = painterResource(id = R.drawable.loading_book),
                contentDescription = null
            )
            Icon(
                modifier = modifier
                    .size(36.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 6.dp)
                    .clickable { isBookmark = !isBookmark },
                painter = if (isBookmark) painterResource(id = R.drawable.bookmark_filled)
                else painterResource(id = R.drawable.bookmark_border),
                tint = if (isBookmark) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.surface,
                contentDescription = null
            )
        }

        Text(
            modifier = modifier
                .padding(top = 4.dp, start = 4.dp),
            text = title,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            modifier = modifier
                .padding(top = 2.dp, start = 4.dp),
            text = author,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBookItem() {
    BookItem(
        modifier = Modifier,
        model = null,
        title = "제목",
        author = "저자",
        onItemClick = {}
    )
}