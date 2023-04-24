package com.example.pinpointapp.presentation.screen.saved

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backendless.Backendless
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var savedSets by mutableStateOf(emptyList<PointSet>())
        private set

    init {
        getSavedSets()
        viewModelScope.launch {
            repository.observeSavedSets(Backendless.UserService.CurrentUser().objectId).collect {
                getSavedSets()
            }
        }
    }

    private fun getSavedSets() {
        viewModelScope.launch(Dispatchers.IO) {
            savedSets =
                repository.getSavedSets(userObjectId = Backendless.UserService.CurrentUser().objectId)
        }

    }
}