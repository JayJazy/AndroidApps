package com.example.kakaobooksearchapp.presentation.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kakaobooksearchapp.R
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.presentation.component.AsyncFailImage
import com.example.kakaobooksearchapp.presentation.navigtation.model.BookNavItem

@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    bookData: Document,
    isBookmarked: Boolean = false,
    onBookmarkClick: () -> Unit = {},
    onItemClick: (String) -> Unit,
    onItemSet: (Document) -> Unit
) {
    var isBookmark by remember { mutableStateOf(isBookmarked) }
    Column(
        modifier = modifier
            .clickable {
                onItemSet(bookData)
                onItemClick(BookNavItem.BookDetailItem.route)
            }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            AsyncFailImage(
                modifier = modifier
                    .fillMaxSize(),
                model = bookData.thumbnail,
                contentScale = ContentScale.Fit
            )
            Icon(
                modifier = modifier
                    .size(52.dp)
                    .align(Alignment.TopEnd)
                    .padding(end = 20.dp)
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
            text = bookData.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            modifier = modifier
                .padding(top = 2.dp, start = 4.dp),
            text = bookData.authors.joinToString(
                separator = stringResource(id = R.string.text_comma),
                prefix = stringResource(id = R.string.text_author)
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBookItem() {
    BookItem(
        modifier = Modifier,
        onItemClick = {},
        bookData = Document(
            authors = listOf("저자1", "저자2"),
            contents = "",
            datetime = "",
            isbn = "",
            price = 0,
            publisher = "",
            salePrice = 0,
            status = "",
            thumbnail = "",
            title = "제목 입니다.",
            translators = listOf(),
            url = ""
        ),
        isBookmarked = false,
        onBookmarkClick = {},
        onItemSet = {}
    )
}