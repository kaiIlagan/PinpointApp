package com.example.pinpointapp.presentation.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinpointapp.domain.model.MessageBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/
class LoginViewModel(
    savedStateHandle: SavedStateHandle //Handles arguments sent by navigation compose for login
) : ViewModel() {

    var signedInState by mutableStateOf(
        value = savedStateHandle.get<Boolean>("signedInState") ?: true
    )
        private set

    var messageBarState by mutableStateOf(MessageBarState())
        private set

    fun updateSignedInState(signedIn: Boolean) {
        signedInState = signedIn
    }

    fun updateMessageBarState(message: String) {
        messageBarState = MessageBarState(error = Exception(message))
    }

}