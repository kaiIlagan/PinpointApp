package com.example.pinpointapp.presentation.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pinpointapp.domain.model.MessageBarState
import java.lang.Exception

class LoginViewModel: ViewModel() {

    var signedInState by mutableStateOf(false)
        private set

    var messageBarState by mutableStateOf(MessageBarState())
        private set

    fun updateSignedInState(signedIn: Boolean){
        signedInState = signedIn
    }

    fun updateMessageBarState(message: String){
        messageBarState = MessageBarState(error = Exception(message))
    }

}