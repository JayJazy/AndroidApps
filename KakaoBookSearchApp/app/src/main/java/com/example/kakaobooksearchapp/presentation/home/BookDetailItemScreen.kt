package com.example.kakaobooksearchapp.presentation.home

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kakaobooksearchapp.R
import com.example.kakaobooksearchapp.data.model.Document
import com.example.kakaobooksearchapp.presentation.component.AsyncFailImage
import com.example.kakaobooksearchapp.presentation.component.ErrorScreen
import com.example.kakaobooksearchapp.presentation.component.ShimmerSpacer
import com.example.kakaobooksearchapp.presentation.component.shimmerEffect
import com.example.kakaobooksearchapp.presentation.model.BookListState
import com.example.kakaobooksearchapp.presentation.model.dummyDocumentList
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel

@Composable
fun BookDetailItemScreen(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val shimmerBrush = shimmerEffect()
    val shimmerEffectModifier = Modifier
        .padding(start = 12.dp, end = 5.dp)
        .background(
            shape = RoundedCornerShape(12.dp),
            brush = shimmerBrush
        )

    when (uiState) {
        is BookListState.Loading -> {
            BookDetailItemScreen(
                modifier = modifier,
                shimmerEffectModifier = shimmerEffectModifier,
                isShimmerEffect = true,
                bookData = dummyDocumentList().component1()
            )
        }

        is BookListState.Error -> {
            ErrorScreen()
        }

        is BookListState.Success -> {
            val value = uiState as BookListState.Success

            BookDetailItemScreen(
                modifier = modifier,
                bookData = value.bookDetailItem ?: return
            )
        }
    }
}

@Composable
fun BookDetailItemScreen(
    modifier: Modifier = Modifier,
    shimmerEffectModifier: Modifier = Modifier,
    isShimmerEffect: Boolean = false,
    bookData: Document
) {
    val shimmerModifier = if (isShimmerEffect) {
        shimmerEffectModifier
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
                    AsyncFailImage(
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
                        style = MaterialTheme.typography.bodySmall,
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
fun PreviewBookDetailItemScreen(){
    BookDetailItemScreen(
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