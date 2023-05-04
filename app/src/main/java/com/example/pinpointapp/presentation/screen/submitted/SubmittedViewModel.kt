package com.example.pinpointapp.presentation.screen.submitted

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backendless.Backendless
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.domain.repository.Repository
import com.example.pinpointapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/
@HiltViewModel
class SubmittedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _submittedSets = mutableStateListOf<PointSet>()
    val submittedSets: List<PointSet> = _submittedSets

    var requestState by mutableStateOf<RequestState>(RequestState.Idle)
        private set

    init {
        getSubmittedSets()
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeSubmittedSets(
                userObjectId = Backendless.UserService.CurrentUser().objectId
            ).collect { pointSet ->
                _submittedSets.add(pointSet)
            }

        }
    }

    private fun getSubmittedSets() {
        requestState = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = _submittedSets.addAll(
                repository.getSubmittedSets(
                    userObjectId = Backendless.UserService.CurrentUser().objectId
                )
            )
            requestState = if (result) RequestState.Success else RequestState.Error
        }
    }
}