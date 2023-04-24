package com.example.pinpointapp.presentation.screen.saved

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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    private val _savedSets = mutableStateListOf<PointSet>()
    val savedSets: List<PointSet> = _savedSets

    var requestState by mutableStateOf<RequestState>(RequestState.Idle)
        private set

    init {
        getSavedSets()
        viewModelScope.launch {
            repository.observeSavedSets(Backendless.UserService.CurrentUser().objectId)
                .collect { status ->
                    _savedSets.removeAll {
                        it.objectId == status?.children?.first()
                    }
                }
        }
    }

    private fun getSavedSets() {
        requestState = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = _savedSets.addAll(
                repository.getSavedSets(Backendless.UserService.CurrentUser().objectId)
            )
            requestState = if (result) RequestState.Success else RequestState.Error
        }


    }
}