package com.example.pinpointapp.presentation.screen.details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pinpointapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
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

    var isPinned by mutableStateOf(false)

    var selectedSet by mutableStateOf(PointSet())
        private set

    private val _uiEvent = Channel<DetailsScreenUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun updateSelectedSet(pointSet: PointSet) {
        selectedSet = pointSet
        checkSavedSet(pointSet.objectId!!)
        checkPinnedSet(pointSet.objectId!!)
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

    private fun checkPinnedSet(objectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.checkPinnedSet(
                setObjectId = objectId,
                userObjectId = Backendless.UserService.CurrentUser().objectId
            )

            isPinned = result.any { it.objectId == objectId }
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

    fun pinPointSet() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.pinPointSet(
                    setObjectId = selectedSet!!.objectId!!,
                    userObjectId = Backendless.UserService.CurrentUser().objectId
                )

                if (result == 0) {
                    repository.unsavePointSet(
                        setObjectId = selectedSet!!.objectId!!,
                        userObjectId = Backendless.UserService.CurrentUser().objectId
                    )
                    isPinned = false
                    _uiEvent.send(DetailsScreenUIEvent.unPinSet)
                } else if (result > 0) {
                    isPinned = true
                    _uiEvent.send(DetailsScreenUIEvent.PinSet)
                }
            } catch (e: Exception) {
                _uiEvent.send(DetailsScreenUIEvent.Error(text = isConnectionError(message = e.message.toString())))
            }
        }
    }

    fun addLike(userObjectId: String = Backendless.UserService.CurrentUser().objectId) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.addLike(selectedSet.objectId!!, userObjectId)
                Log.d("addLike", result.toString())
                if (result == 0) {
                    repository.removeLike(
                        setObjectId = selectedSet.objectId!!,
                        userObjectId = userObjectId
                    )
                    selectedSet =
                        selectedSet.totalLikes?.minus(1).let { selectedSet.copy(totalLikes = it) }
                    _uiEvent.send(DetailsScreenUIEvent.unLikeSet)
                } else if (result != null && result > 0) {
                    selectedSet =
                        selectedSet.totalLikes?.plus(1).let { selectedSet.copy(totalLikes = it) }
                    _uiEvent.send(DetailsScreenUIEvent.LikeSet)
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
    object PinSet : DetailsScreenUIEvent(message = "Pinned!")
    object unPinSet : DetailsScreenUIEvent(message = "Removed from Pinned!")

    object LikeSet : DetailsScreenUIEvent(message = "Liked!")
    object unLikeSet : DetailsScreenUIEvent(message = "Removed from Liked!")

    data class Error(val text: String) : DetailsScreenUIEvent(message = text)
    //Maybe Copy Points for google maps?
}