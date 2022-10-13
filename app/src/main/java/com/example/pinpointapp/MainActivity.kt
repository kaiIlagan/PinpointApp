package com.example.pinpointapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.backendless.Backendless
import com.example.pinpointapp.keys.Keys.API_KEY
import com.example.pinpointapp.keys.Keys.APP_ID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Backendless.initApp(this, APP_ID, API_KEY)

    }
}

