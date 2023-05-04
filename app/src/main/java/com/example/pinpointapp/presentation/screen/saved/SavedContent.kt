package com.example.pinpointapp.presentation.screen.saved

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.presentation.components.DefaultContent
import com.example.pinpointapp.util.RequestState

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

@Composable
fun SavedContent(
    navController: NavHostController,
    savedSet: List<PointSet>,
    requestState: RequestState
) {
    when (requestState) {
        is RequestState.Success -> {
            if (savedSet.isEmpty()) {
                NoSavedSets()
            } else {
                DefaultContent(navController = navController, pointSets = savedSet)
            }
        }
        is RequestState.Error -> {
            if (savedSet.isEmpty()) {
                NoSavedSets()
            }
        }
        else -> {}
    }
}

@Composable
fun NoSavedSets() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No Saved Point Sets",
            style = TextStyle(
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
