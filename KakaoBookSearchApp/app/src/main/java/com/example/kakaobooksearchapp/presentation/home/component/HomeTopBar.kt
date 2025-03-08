package com.example.kakaobooksearchapp.presentation.home.component

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kakaobooksearchapp.R

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    isDetailScreen: Boolean,
    onSearchClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    var searchText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val emptySearchText = stringResource(id = R.string.empty_search_text)
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (!isDetailScreen) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(1f)
                    .fillMaxWidth()
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                value = searchText,
                singleLine = false,
                onValueChange = { inputText ->
                    if (inputText.length <= 40) {
                        searchText = inputText
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.text_field_label),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                ),
                keyboardActions = KeyboardActions(onSearch = { onSearchClick(searchText) }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                trailingIcon = {
                    if(isFocused && searchText.isNotEmpty()){
                        Icon(
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    searchText = ""
                                },
                            imageVector = ImageVector.vectorResource(id = R.drawable.cancel),
                            contentDescription = null
                        )
                    }
                }
            )
            Icon(
                modifier = Modifier
                    .size(72.dp)
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
                    .clickable {
                        if(searchText.isNotEmpty()){
                            onSearchClick(searchText)
                        } else {
                            Toast.makeText(context, emptySearchText, Toast.LENGTH_SHORT).show()
                        }
                    },
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        } else {
            Icon(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable { onBackClick() },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                text = stringResource(id = R.string.text_book_detail),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomeTopBar1(){
    HomeTopBar(
        modifier = Modifier,
        isDetailScreen = true,
        onSearchClick = {},
        onBackClick = {},
    )
}

@Preview
@Composable
fun PreviewHomeTopBar2(){
    HomeTopBar(
        modifier = Modifier,
        isDetailScreen = false,
        onSearchClick = {},
        onBackClick = {},
    )
}