package com.example.kakaobooksearchapp.presentation.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kakaobooksearchapp.R
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.presentation.component.AsyncImageHandleComponent
import com.example.kakaobooksearchapp.presentation.component.ShimmerSpacer
import com.example.kakaobooksearchapp.presentation.component.shimmerEffect
import com.example.kakaobooksearchapp.presentation.model.BookListState
import com.example.kakaobooksearchapp.presentation.model.dummyBook
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel


@Composable
fun BookDetailScreen(
    viewModel: BookViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is BookListState.Loading -> {
            BookDetailContent(
                bookData = dummyBook(),
                modifier = modifier,
                isShimmerEffect = true,
            )
        }

        is BookListState.Error -> {
            ErrorScreen()
        }

        is BookListState.Success -> {
            val value = uiState as BookListState.Success

            BookDetailContent(
                bookData = value.bookDetail ?: dummyBook(),
                modifier = modifier
            )
        }
    }
}

@Composable
fun BookDetailContent(
    bookData: Document,
    modifier: Modifier = Modifier,
    isShimmerEffect: Boolean = false
) {
    val shimmerModifier = if (isShimmerEffect) {
        Modifier
            .padding(start = 12.dp, end = 5.dp)
            .background(
                shape = RoundedCornerShape(12.dp),
                brush = shimmerEffect()
            )
    } else Modifier

    val textShimmerModifier = if (isShimmerEffect) {
        shimmerModifier
            .fillMaxWidth()
            .size(16.dp)
    } else Modifier


    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = shimmerModifier
                    .size(136.dp)
                    .padding(start = 20.dp)
                    .aspectRatio(0.7f)
            ) {
                if (!isShimmerEffect) {
                    AsyncImageHandleComponent(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        model = bookData.thumbnail
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 12.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = textShimmerModifier
                        .padding(vertical = 2.dp),
                    text = bookData.title,
                    style = MaterialTheme.typography.titleMedium
                )

                ShimmerSpacer(isShimmerEffect = isShimmerEffect)

                Text(
                    modifier = textShimmerModifier
                        .padding(vertical = 2.dp),
                    text = bookData.authors.joinToString(
                        separator = stringResource(id = R.string.text_comma),
                        prefix = if (isShimmerEffect) "" else stringResource(id = R.string.text_author)
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )

                ShimmerSpacer(isShimmerEffect = isShimmerEffect)

                Text(
                    modifier = textShimmerModifier
                        .padding(vertical = 2.dp),
                    text = if (isShimmerEffect) "" else stringResource(id = R.string.text_publisher) + bookData.publisher,
                    style = MaterialTheme.typography.bodyMedium
                )

                ShimmerSpacer(isShimmerEffect = isShimmerEffect)

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = textShimmerModifier
                            .padding(vertical = 2.dp)
                            .padding(end = 3.dp),
                        text = if (isShimmerEffect) "" else bookData.price.toString() + stringResource(id = R.string.text_won),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = colorResource(id = R.color.light_gray),
                            textDecoration = TextDecoration.LineThrough
                        ),
                    )

                    Text(
                        modifier = textShimmerModifier
                            .padding(vertical = 2.dp)
                            .padding(start = 3.dp),
                        text = if (isShimmerEffect) "" else bookData.salePrice.toString() + stringResource(id = R.string.text_won),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.secondary
                        ),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            modifier = textShimmerModifier
                .padding(horizontal = 20.dp),
            text = bookData.contents,
            style = MaterialTheme.typography.bodyMedium,
        )

        if (isShimmerEffect) {

            ShimmerSpacer(isShimmerEffect = true)

            Text(
                modifier = textShimmerModifier
                    .padding(horizontal = 20.dp),
                text = ""
            )

            ShimmerSpacer(isShimmerEffect = true)

            Text(
                modifier = textShimmerModifier
                    .padding(horizontal = 20.dp),
                text = ""
            )
        }
    }
}

@Preview
@Composable
fun PreviewBookDetailContent(){
    BookDetailContent(
        modifier = Modifier,
        bookData = Document(
            authors = listOf("저자1", "저자2"),
            contents = "Cathy",
            datetime = "Leighton",
            isbn = "Phebe",
            price = 6830,
            publisher = "Ronaldo",
            salePrice = 9515,
            status = "Harriet",
            thumbnail = "Miriam",
            title = "Turquoise",
            translators = listOf(),
            url = "Jamecia"
        )
    )
}