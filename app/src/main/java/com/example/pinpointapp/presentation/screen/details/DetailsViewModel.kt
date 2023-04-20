package com.example.pinpointapp.presentation.screen.details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pinpointapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.backendless.Backendless
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.presentation.screen.isConnectionError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var isSaved by mutableStateOf(false)
        private set

    var selectedSet by mutableStateOf(PointSet())
        private set

    private val _uiEvent = Channel<DetailsScreenUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun updateSelectedSet(pointSet: PointSet) {
        selectedSet = pointSet
        checkSavedSet(pointSet.objectId!!)
    }

    private fun checkSavedSet(objectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.checkSavedSet(
                setObjectId = objectId,
                userObjectId = Backendless.UserService.CurrentUser().objectId
            )

            Log.d("checkSavedSet", result.toString())
            Log.d("checkSavedSet", "${result.any { it.objectId == objectId }}")
            isSaved = result.any { it.objectId == objectId }
        }
    }

    fun savePointSet() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.savePointSet(
                    setObjectId = selectedSet.objectId!!,
                    userObjectId = Backendless.UserService.CurrentUser().objectId
                )

                if (result == 0) {
                    repository.unsavePointSet(
                        setObjectId = selectedSet.objectId!!,
                        userObjectId = Backendless.UserService.CurrentUser().objectId
                    )
                    isSaved = false
                    _uiEvent.send(DetailsScreenUIEvent.unSaveSet)
                } else if (result > 0) {
                    isSaved = true
                    _uiEvent.send(DetailsScreenUIEvent.SaveSet)
                }
            } catch (e: Exception) {
                _uiEvent.send(DetailsScreenUIEvent.Error(text = isConnectionError(message = e.message.toString())))
            }
        }
    }
}

sealed class DetailsScreenUIEvent(val message: String) {
    object SaveSet : DetailsScreenUIEvent(message = "Saved!")
    object unSaveSet : DetailsScreenUIEvent(message = "Removed from Saved!")
    data class Error(val text: String) : DetailsScreenUIEvent(message = text)
}