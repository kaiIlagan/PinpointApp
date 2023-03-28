package com.example.pinpointapp.presentation.screen.data

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pinpointapp.presentation.components.DefaultContent
import com.example.pinpointapp.presentation.components.NavigationDrawer
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DataScreen(
    navController: NavHostController,
    dataViewModel: DataViewModel = hiltViewModel()
) {
    val pointSets = dataViewModel.pointSets
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { DataTopBar(scaffoldState = scaffoldState) },
        drawerContent = {
            NavigationDrawer(
                navController = navController,
                scaffoldState = scaffoldState,
                logoutFailed = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Something went wrong.",
                            actionLabel = "OK"
                        )
                    }
                }
            )
        },
        content = {
            DefaultContent(navController = navController, pointSets = pointSets)
        }
    )
}