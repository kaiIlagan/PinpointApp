package com.example.pinpointapp.presentation.screen.submitted

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.domain.repository.Repository
import com.example.pinpointapp.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubmittedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _submittedSets = mutableStateListOf<PointSet>()
    val submittedSets: List<PointSet> = _submittedSets

    var requestState by mutableStateOf<RequestState>(RequestState.Success)
        private set
}