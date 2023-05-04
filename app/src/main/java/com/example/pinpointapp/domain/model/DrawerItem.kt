package com.example.pinpointapp.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pinpointapp.navigation.Screen

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

sealed class DrawerItem(
    val icon: ImageVector,
    val title: String,
    val route: String
) {
    object Map : DrawerItem(
        icon = Icons.Default.Map,
        title = "Map",
        route = Screen.Map.route
    )

    object Data : DrawerItem(
        icon = Icons.Default.Storage,
        title = "Point Database",
        route = Screen.Data.route
    )

    object Saved : DrawerItem(
        icon = Icons.Default.Bookmarks,
        title = "Saved Points",
        route = Screen.Saved.route
    )

    object Pinned : DrawerItem(
        icon = Icons.Default.PushPin,
        title = "Pinned Points",
        route = Screen.Pinned.route
    )

    object Submitted : DrawerItem(
        icon = Icons.Default.CloudUpload,
        title = "Submitted Points",
        route = Screen.Submitted.route
    )

    object Create : DrawerItem(
        icon = Icons.Default.CreateNewFolder,
        title = "Create Point Set",
        route = Screen.Create.route
    )

    object Logout : DrawerItem(
        icon = Icons.Default.Logout,
        title = "Logout",
        route = Screen.Login.route
    )

}
