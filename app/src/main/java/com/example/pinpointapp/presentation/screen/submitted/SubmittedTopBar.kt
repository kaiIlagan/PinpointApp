package com.example.pinpointapp.presentation.screen.submitted

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.ui.theme.topAppBarBackgroundColor
import com.example.pinpointapp.ui.theme.topAppBarContentColor
import com.example.pinpointapp.util.RequestState

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/
@Composable
fun SubmittedTopBar(
    submittedSets: List<PointSet>,
    requestState: RequestState,
    onSubmitClicked: () -> Unit,
    onMenuClicked: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Drawer Icon",
                    tint = MaterialTheme.colors.topAppBarContentColor
                )
            }
        },
        title = {
            if (requestState is RequestState.Success || requestState is RequestState.Error) {
                Text(
                    text = if (submittedSets.isNotEmpty()) "Submit" else "Submitted Point Sets",
                    color = MaterialTheme.colors.topAppBarContentColor
                )
            }
        },
        actions = {
            IconButton(onClick = onSubmitClicked) {
                Icon(
                    imageVector = Icons.Filled.CloudUpload,
                    contentDescription = "Submit Icon",
                    tint = MaterialTheme.colors.topAppBarContentColor
                )
            }
        }
    )
}

@Preview
@Composable
fun SubmittedTopBarP1() {
    SubmittedTopBar(
        submittedSets = emptyList(),
        requestState = RequestState.Success,
        onSubmitClicked = {},
        onMenuClicked = {})
}

@Preview
@Composable
fun SubmittedTopBarP2() {
    SubmittedTopBar(
        submittedSets = listOf(PointSet()),
        requestState = RequestState.Success,
        onSubmitClicked = {},
        onMenuClicked = {})
}