package com.example.pinpointapp.presentation.screen.create

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pinpointapp.domain.model.PointSet
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateScreen(
    navController: NavHostController,
    createViewModel: CreateViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        createViewModel.uiEvent.collect {
            scaffoldState.snackbarHostState.showSnackbar(message = it.message, actionLabel = "OK")
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { CreateTopBar(onBackClicked = { navController.popBackStack() }) },
        content = {
            CreateContent(
                onSubmitClicked = { title, desc, points ->
                    createViewModel.submitPointSet(
                        pointSet = PointSet(title = title, desc = desc, points = points)
                    )
                }
            )
        }
    )
}