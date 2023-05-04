package com.example.pinpointapp.presentation.screen.login

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.example.pinpointapp.ui.theme.topAppBarBackgroundColor
import com.example.pinpointapp.ui.theme.topAppBarContentColor

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/
@Composable
fun LoginTopBar() {
    TopAppBar(
        title = {
            Text(text = "Sign in", color = MaterialTheme.colors.topAppBarContentColor)
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}