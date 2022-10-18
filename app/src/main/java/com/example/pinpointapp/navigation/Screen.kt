package com.example.pinpointapp.navigation

// Please see readme.txt for attributions of code
sealed class Screen(val route: String) {
    object Login : Screen(route = "login")
    object Home : Screen(route = "home")
    object Details : Screen(route = "details")
    object Saved : Screen(route = "saved")
    object Pinned : Screen(route = "pinned")
    object Map : Screen(route = "map")
    object Submitted : Screen(route = "submitted")
    object Create : Screen(route = "create")
}
