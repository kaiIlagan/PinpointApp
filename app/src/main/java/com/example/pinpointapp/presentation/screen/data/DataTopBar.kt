package com.example.pinpointapp.presentation.screen.data

import android.graphics.drawable.Icon
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.example.pinpointapp.ui.theme.topAppBarBackgroundColor
import com.example.pinpointapp.ui.theme.topAppBarContentColor
import kotlinx.coroutines.launch

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

@Composable
fun DataTopBar(scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        navigationIcon = {
            IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Drawer Icon",
                    tint = MaterialTheme.colors.topAppBarContentColor
                )
            }
        },
        title = {
            Text(
                text = "Data", color = MaterialTheme.colors.topAppBarContentColor
            )
        }
    )
}

@Preview
@Composable
fun DataTopBarPreview() {
    DataTopBar(scaffoldState = rememberScaffoldState())
}