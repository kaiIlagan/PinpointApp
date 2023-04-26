package com.example.pinpointapp.presentation.screen.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.domain.repository.Repository
import com.example.pinpointapp.presentation.screen.isConnectionError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiEvent = Channel<CreateScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun submitPointSet(pointSet: PointSet) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.submitSet(pointSet)
                _uiEvent.send(CreateScreenUiEvent.Submitted)
            } catch (e: Exception) {
                _uiEvent.send(CreateScreenUiEvent.Error(isConnectionError(e.message.toString())))
            }
        }
    }
}

sealed class CreateScreenUiEvent(val message: String) {
    object Submitted : CreateScreenUiEvent(message = "Successfully Submitted!")
    data class Error(val error: String) : CreateScreenUiEvent(message = error)
}