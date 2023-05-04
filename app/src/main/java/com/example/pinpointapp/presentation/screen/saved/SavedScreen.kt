package com.example.pinpointapp.presentation.screen.saved

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pinpointapp.presentation.components.NavigationDrawer
import kotlinx.coroutines.launch

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SavedScreen(
    navController: NavHostController,
    savedViewModel: SavedViewModel = hiltViewModel()
) {

    val savedSets = savedViewModel.savedSets
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val requestState = savedViewModel.requestState

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SavedTopBar(onMenuClicked = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            })
        },
        drawerContent = {
            NavigationDrawer(
                navController = navController,
                scaffoldState = scaffoldState,
                logoutFailed = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Something went wrong",
                            actionLabel = "OK"
                        )
                    }
                })
        },
        content = {
            SavedContent(
                navController = navController,
                savedSet = savedSets,
                requestState = requestState
            )
        }
    )
}