package com.example.kakaobooksearchapp.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kakaobooksearchapp.R
import com.example.kakaobooksearchapp.data.model.Document

@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    isShimmerEffect: Boolean = false,
    bookData: Document,
    onSetBookDetailItem: (Document) -> Unit
) {
    val shimmerModifier = if (isShimmerEffect) modifier else Modifier

    Column(
        modifier = if (isShimmerEffect) Modifier else modifier
            .clickable { onSetBookDetailItem(bookData) },
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            if (!isShimmerEffect) {
                AsyncFailImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = bookData.thumbnail,
                    contentScale = ContentScale.Fit
                )
            }
        }


        Text(
            modifier = shimmerModifier
                .fillMaxWidth()
                .padding(top = 2.dp, start = 4.dp),
            text = bookData.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )


        Text(
            modifier = shimmerModifier
                .fillMaxWidth()
                .padding(top = 2.dp, start = 4.dp),
            text = bookData.authors.joinToString(
                separator = stringResource(id = R.string.text_comma),
                prefix = if (isShimmerEffect) "" else stringResource(id = R.string.text_author)
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
        isShimmerEffect = false,
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
        onSetBookDetailItem = {}
    )
}