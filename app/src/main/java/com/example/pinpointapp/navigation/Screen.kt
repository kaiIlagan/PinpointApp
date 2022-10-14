package com.example.pinpointapp.navigation

sealed class Screen(val route: String){
    object Login: Screen(route = "login")
    object Home: Screen(route = "home")
    object Details: Screen(route = "details")
    object Liked: Screen(route = "liked")
    object Saved: Screen(route = "saved")
    object Submitted: Screen(route ="submitted")
    object Create: Screen(route = "create")
}
