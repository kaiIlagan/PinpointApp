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
                        text = "${pointSet.totalLikes ?: "0"}",
                        color = Color.White
                    )
                },
                onClick = { })
        }
    )
}
