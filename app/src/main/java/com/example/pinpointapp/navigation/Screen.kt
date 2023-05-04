package com.example.pinpointapp.navigation

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

sealed class Screen(val route: String) {
    object Login : Screen(route = "login/{signedInState}") {
        fun passSignedInState(signedInState: Boolean = true) = "login/$signedInState"
    }

    object Data : Screen(route = "data")
    object Details : Screen(route = "details")
    object Saved : Screen(route = "saved")
    object Pinned : Screen(route = "pinned")
    object Map : Screen(route = "map")
    object Submitted : Screen(route = "submitted")
    object Create : Screen(route = "create")
}
