package com.example.pinpointapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {}
        composable(route = Screen.Home.route) {}
        composable(route = Screen.Details.route) {}
        composable(route = Screen.Liked.route) {}
        composable(route = Screen.Saved.route) {}
        composable(route = Screen.Submitted.route) {}
        composable(route = Screen.Create.route) {}
    }
}