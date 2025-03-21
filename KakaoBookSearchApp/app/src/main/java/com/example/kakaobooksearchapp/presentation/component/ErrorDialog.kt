package com.example.kakaobooksearchapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.kakaobooksearchapp.R

@Composable
fun ErrorDialog(
    requestBookList: () -> Unit
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    shape = RoundedCornerShape(10.dp)
                )
                .width(280.dp)
                .height(160.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.error_message),
                style = MaterialTheme.typography.displayMedium.copy(
                    color = MaterialTheme.colorScheme.error
                )
            )

            TextButton(
                modifier = Modifier
                    .align(Alignment.End),
                onClick = { requestBookList() }) {
                Text(
                    text = stringResource(id = R.string.error_confirm),
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewErrorDialog(){
    ErrorDialog(
        requestBookList = {}
    )
}