package com.example.pinpointapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pinpointapp.presentation.screen.map.MapScreen
import com.example.pinpointapp.presentation.screen.login.LoginScreen

// Please see readme.txt for attributions of code
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Map.route) {
            MapScreen(navController = navController)
        }
        composable(route = Screen.Data.route) {}
        composable(route = Screen.Details.route) {}
        composable(route = Screen.Pinned.route) {}
        composable(route = Screen.Saved.route) {}
        composable(route = Screen.Submitted.route) {}
        composable(route = Screen.Create.route) {}
    }
}