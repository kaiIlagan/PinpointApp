package com.example.pinpointapp.util

sealed class RequestState {
    object Idle : RequestState()
    object Loading : RequestState()
    object Success : RequestState()
    object Error : RequestState()
}
