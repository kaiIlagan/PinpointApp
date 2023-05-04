package com.example.pinpointapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.backendless.Backendless
import com.example.pinpointapp.keys.Keys.API_KEY
import com.example.pinpointapp.keys.Keys.APP_ID
import com.example.pinpointapp.navigation.SetupNavGraph
import com.example.pinpointapp.presentation.screen.logout
import com.example.pinpointapp.ui.theme.PinpointAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.AndroidEntryPoint

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onResume() {
        super.onResume()
        logout(onSuccess = {}, onFailed = {})
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Backendless.initApp(this, APP_ID, API_KEY)

        setContent {
            PinpointAppTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }

    }
}

