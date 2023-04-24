package com.example.pinpointapp.presentation.screen.pinned

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pinpointapp.presentation.components.NavigationDrawer
import com.example.pinpointapp.presentation.screen.saved.SavedContent
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PinnedScreen(
    navController: NavHostController,
    pinnedViewModel: PinnedViewModel = hiltViewModel()
) {

    val pinnedSets = pinnedViewModel.pinnedSets
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val requestState = pinnedViewModel.requestState

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PinnedTopBar(onMenuClicked = {
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
            PinnedContent(
                navController = navController,
                pinnedSet = pinnedSets,
                requestState = requestState
            )
        }
    )
}