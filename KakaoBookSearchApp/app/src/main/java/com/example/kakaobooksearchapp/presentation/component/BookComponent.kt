package com.example.kakaobooksearchapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kakaobooksearchapp.R
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.data.model.Document.Companion.dummyBook

@Composable
fun BookComponent(
    bookDetail: Document,
    onBookClick: (Document) -> Unit,
    modifier: Modifier = Modifier,
    isShimmerEffect: Boolean = false,
) {
    val brush = if (isShimmerEffect) shimmerEffect() else SolidColor(Color.Transparent)
    val shimmerHeight = if (isShimmerEffect) 4.dp else 0.dp

    Column(
        modifier = modifier
            .clickable { onBookClick(bookDetail) },
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = brush,
                    shape = RoundedCornerShape(12.dp)
                )
                .fillMaxWidth()
                .height(160.dp)
        ) {
            if (!isShimmerEffect) {
                AsyncImageHandleComponent(
                    model = bookDetail.thumbnail,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.height(shimmerHeight))

        Text(
            modifier = Modifier
                .background(
                    brush = brush,
                    shape = RoundedCornerShape(12.dp)
                )
                .fillMaxWidth()
                .padding(top = 2.dp, start = 4.dp),
            text = bookDetail.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(shimmerHeight))

        Text(
            modifier = Modifier
                .background(
                    brush = brush,
                    shape = RoundedCornerShape(12.dp)
                )
                .fillMaxWidth()
                .padding(top = 2.dp, start = 4.dp),
            text = bookDetail.authors.joinToString(
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
fun PreviewBookComponent() {
    BookComponent(
        isShimmerEffect = false,
        bookDetail = dummyBook(),
        onBookClick = {}
    )
}