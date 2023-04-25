package com.example.pinpointapp.presentation.screen.map

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
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

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _mapSets = mutableStateListOf<PointSet>()
    val mapSets: List<PointSet> = _mapSets

    init {
        getMapSets()
        viewModelScope.launch {
            repository.observePinnedSets(Backendless.UserService.CurrentUser().objectId)
                .collect {
                    _mapSets.removeAll(_mapSets)
                    getMapSets()
                }
        }
    }


    private fun getMapSets() {
        viewModelScope.launch(Dispatchers.IO) {
            _mapSets.addAll(
                repository.getPinnedSets(Backendless.UserService.CurrentUser().objectId)
            )
            mapSets.forEach {
                Log.d("getMapSets", it.toString())
            }
        }
    }
}