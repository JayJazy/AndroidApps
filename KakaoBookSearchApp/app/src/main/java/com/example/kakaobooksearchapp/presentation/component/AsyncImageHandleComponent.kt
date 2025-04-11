package com.example.kakaobooksearchapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.kakaobooksearchapp.R

@Composable
fun AsyncImageHandleComponent(
    model: String,
    modifier : Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    defaultImageSize: Dp = 65.dp
) {
    var imageState by remember { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }

    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = model,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            onState = { state ->
                imageState = state
            },
            contentScale = contentScale
        )

        if (imageState !is AsyncImagePainter.State.Success) {
            ImageLoadingComponent(
                defaultImageSize = defaultImageSize,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun ImageLoadingComponent(
    defaultImageSize: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.onSurface,
                RoundedCornerShape(16.dp),
            )
            .padding(16.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.loading_book),
            contentDescription = null,
            modifier = Modifier
                .size(defaultImageSize)
                .align(Alignment.Center),
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAsyncImageHandleComponent(){
    AsyncImageHandleComponent(
        model = ""
    )
}