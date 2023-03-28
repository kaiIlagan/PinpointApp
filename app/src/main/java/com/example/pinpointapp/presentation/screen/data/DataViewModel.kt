package com.example.pinpointapp.presentation.screen.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var pointSets = mutableStateListOf<PointSet>()
        private set

    init {
        getPointSets()
    }

    private fun getPointSets() {
        viewModelScope.launch(Dispatchers.IO) {
            pointSets.addAll(repository.getPointSets())
        }
    }
}