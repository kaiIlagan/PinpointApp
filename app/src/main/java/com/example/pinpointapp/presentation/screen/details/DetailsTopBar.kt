package com.example.pinpointapp.presentation.screen.details

import android.telecom.Call.Details
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.pinpointapp.ui.theme.topAppBarBackgroundColor
import com.example.pinpointapp.ui.theme.topAppBarContentColor

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

@Composable
fun DetailsTopBar(
    isSaved: Boolean,
    isPinned: Boolean,
    onBackClicked: () -> Unit,
    onPinClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Arrow Icon",
                    tint = MaterialTheme.colors.topAppBarContentColor
                )

            }
        },
        title = {
            Text(
                text = "Details",
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        actions = {
            IconButton(onClick = onPinClicked) {
                Icon(
                    imageVector = Icons.Filled.PushPin,
                    contentDescription = "Push Pin Icon",
                    tint = if (isPinned) Color.Red
                    else MaterialTheme.colors.topAppBarContentColor
                )
            }
            IconButton(onClick = onSaveClicked) {
                Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = "Bookmark Icon",
                    tint = if (isSaved) Color.Yellow
                    else MaterialTheme.colors.topAppBarContentColor
                )
            }
        }
    )
}

@Preview
@Composable
fun DetailsTopBarPreview() {
    DetailsTopBar(
        isSaved = false,
        isPinned = false,
        onBackClicked = { },
        onPinClicked = { },
        onSaveClicked = {},
    )
}

@Preview
@Composable
fun DetailsTopBarPinnedPreview() {
    DetailsTopBar(
        isSaved = false,
        isPinned = true,
        onBackClicked = { },
        onPinClicked = { },
        onSaveClicked = {},
    )
}

@Preview
@Composable
fun DetailsTopBarSavedPreview() {
    DetailsTopBar(
        isSaved = true,
        isPinned = false,
        onBackClicked = { },
        onPinClicked = { },
        onSaveClicked = {},
    )
}

@Preview
@Composable
fun DetailsTopBarBothPreview() {
    DetailsTopBar(
        isSaved = true,
        isPinned = true,
        onBackClicked = { },
        onPinClicked = { },
        onSaveClicked = {},
    )
}