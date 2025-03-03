package com.example.kakaobooksearchapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.kakaobooksearchapp.R
import com.example.kakaobooksearchapp.presentation.viewmodel.BookViewModel

@Composable
fun BookDetailItemScreen(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel(),
) {
    BookDetailItemScreen(
        modifier = modifier
    )
}

@Composable
fun BookDetailItemScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(136.dp)
                    .padding(start = 20.dp)
                    .aspectRatio(0.7f)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    model = "",
                    placeholder = painterResource(id = R.drawable.loading_book),
                    error = painterResource(id = R.drawable.loading_book),
                    contentDescription = null
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 12.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp),
                    text = "제목",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp),
                    text = "저자",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    modifier = Modifier
                        .padding(vertical = 2.dp),
                    text = "출판사",
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .padding(end = 3.dp),
                        text = "15000원",
                        style = MaterialTheme.typography.bodySmall,
                    )

                    Text(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .padding(start = 3.dp),
                        text = "12000원",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 20.dp),
            text = "내용 입니다. 내용 입니다. 내용 입니다. 내용 입니다. 내용 입니다.",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
fun PreviewBookDetailItemScreen(){
    BookDetailItemScreen(
        modifier = Modifier
    )
}