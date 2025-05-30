package com.example.presentation.main.setting


import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presentation.component.SNSProfileImage
import com.example.presentation.login.LoginActivity
import com.example.snsapp.ui.theme.SNSAppTheme
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun SettingScreen(settingViewModel : SettingViewModel = hiltViewModel())
{

    val context = LocalContext.current
    val state  = settingViewModel.collectAsState().value
    var usernameDialogVisible by remember { mutableStateOf(false) }


    settingViewModel.collectSideEffect { sideEffect ->
        when(sideEffect){
            is SettingSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
            SettingSideEffect.NavigateToLoginActivity -> {
                context.startActivity(Intent(context, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
            }
        }
    }

    SettingScreen(
        userName = state.userName,
        profileImageUrl = state.profileImageUrl,
        onImageChangeClick = { },
        onNameChangeClick = { usernameDialogVisible = true },
        onLogoutClick = settingViewModel::onLogoutClick
    )

    if(usernameDialogVisible){
        UsernameDialog(
            visible = usernameDialogVisible,
            initialUsername = state.userName,
            onUsernameChange = settingViewModel::onUsernameChange,
            onDismissRequest = { usernameDialogVisible = false })
    }

}



@Composable
private fun SettingScreen(
    userName : String,
    profileImageUrl : String?,
    onImageChangeClick : () -> Unit,
    onNameChangeClick : () -> Unit,
    onLogoutClick: () -> Unit
)
{
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            SNSProfileImage(
                modifier = Modifier.size(150.dp),
                profileImageUrl = profileImageUrl
            )

            IconButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = onImageChangeClick
                ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .border(width = 1.dp, color = Color.Gray, shape = CircleShape)
                        .background(color = Color.White, shape = CircleShape)
                )
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(20.dp),
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable{ onNameChangeClick() },
            text = userName,
            style = MaterialTheme.typography.headlineMedium
        )

        Button(onClick = onLogoutClick, modifier = Modifier.padding(top = 16.dp)) {
            Text("로그아웃")
        }
    }
}


@Preview
@Composable
private fun SettingScreenPreview(){
    SNSAppTheme {
        Surface {
            SettingScreen(
                userName = "Jay",
                onLogoutClick = {},
                profileImageUrl = null,
                onImageChangeClick = { },
                onNameChangeClick = { })
        }
    }
}