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

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DataScreen(
    navController: NavHostController,
    dataViewModel: DataViewModel = hiltViewModel()
) {
    val pointSets = dataViewModel.pointSets
    val scaffoldState =
        rememberScaffoldState() //Holds information such as if a drawer is open or if the Snackbar is active
    val scope = rememberCoroutineScope()

    // Scaffold is a Composable Type provided by JetPack Compose to easily create screens, it by default has a trailing lambda for the Content of the Scaffold, but can be outfitted with a topBar, botBar, and more
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