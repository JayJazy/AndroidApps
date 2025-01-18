package com.example.presentation.main

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.presentation.main.writing.WritingActivity
import com.example.snsapp.ui.theme.SNSAppTheme


@Composable
fun MainBottomBar(navController: NavController)
{
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry
        ?.destination
        ?.route
        ?.let{ currentRoute -> MainRoute.entries.find { it.route == currentRoute } }
        ?: MainRoute.BOARD


    MainBottomBar(
        currentRoute = currentRoute,
        onItemClick = { newRoute ->
            if(newRoute == MainRoute.WRITING){
                context.startActivity(Intent(context, WritingActivity::class.java))
            }else
            {
                navController.navigate(newRoute.route){
                    navController.graph.startDestinationRoute?.let {
                        popUpTo(it){
                            saveState = true
                        }
                    }
                    this.launchSingleTop = true
                    this.restoreState = true
                }
            }
        }
    )
}





@Composable
fun MainBottomBar(
    currentRoute: MainRoute,
    onItemClick: (MainRoute) -> Unit
)
{
    Column {
        Divider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            MainRoute.entries.forEach { route ->
                IconButton(onClick = { onItemClick(route) }) {
                    Icon(
                        imageVector = route.icon,
                        contentDescription = route.contentDescription,
                        tint = if (currentRoute == route){
                            MaterialTheme.colorScheme.primary
                        }
                        else{
                            Color.White
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewMainBottomBar()
{
    SNSAppTheme {

        var currentRoute by remember { mutableStateOf(MainRoute.BOARD) }

        MainBottomBar(
            currentRoute = currentRoute,
            onItemClick = { newRoute -> currentRoute = newRoute}
        )
    }

}