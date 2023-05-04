package com.example.pinpointapp.presentation.screen.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pinpointapp.domain.model.PointSet

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavHostController,
    pointSet: PointSet,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    val isSaved = detailsViewModel.isSaved
    val selectedSet = detailsViewModel.selectedSet
    val isPinned = detailsViewModel.isPinned

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = pointSet) {
        detailsViewModel.updateSelectedSet(pointSet = pointSet)
    }

    LaunchedEffect(key1 = Unit) {
        detailsViewModel.uiEvent.collect { uiEvent ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = uiEvent.message,
                actionLabel = "OK"
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DetailsTopBar(
                isSaved = isSaved,
                isPinned = isPinned,
                onBackClicked = { navController.popBackStack() },
                onPinClicked = { detailsViewModel.pinPointSet() },
                onSaveClicked = { detailsViewModel.savePointSet() }
            )
        },
        content = { DetailsContent(pointSet = selectedSet) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 50.dp, 0.dp),
                backgroundColor = Color.Gray,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Heart Icon",
                        tint = Color.White
                    )
                },
                text = {
                    Text(
                        text = "${selectedSet.totalLikes ?: "0"}",
                        color = Color.White
                    )
                },
                onClick = { detailsViewModel.addLike() })
        }
    )
}
