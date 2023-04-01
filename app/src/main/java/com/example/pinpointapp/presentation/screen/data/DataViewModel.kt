package com.example.pinpointapp.presentation.screen.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backendless.rt.data.RelationStatus
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var pointSets = mutableStateListOf<PointSet>()
        private set

    init { // Runs once when DataViewModel is initialized when the User navigates to DataScreen
        getPointSets()
        observeAddRelation()
        observeDeleteRelation()
    }

    private fun getPointSets() {
        viewModelScope.launch(Dispatchers.IO) {
            pointSets.addAll(repository.getPointSets())
        }
    }

    private fun observeAddRelation() { // Observes "listens" to changes to our like column, when it is recieved the data is collected
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeAddRelation().collect { addRelation ->
                Log.d("HomeViewModel", "$addRelation")
                updateNumOfLikes(relationStatus = addRelation)
            }
        }
    }

    private fun observeDeleteRelation() { // Observes "listens" to changes to our like column, when it is recieved the data is collected
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeDeleteRelation().collect { deleteRelation ->
                Log.d("HomeViewModel", "$deleteRelation")
                updateNumOfLikes(relationStatus = deleteRelation)
            }
        }
    }

    private suspend fun updateNumOfLikes(relationStatus: RelationStatus?) {
        val observedSet =
            relationStatus?.parentObjectId?.let { repository.getLikeCount(objectId = it) }
        var position = 0
        var set = PointSet()
        pointSets.forEachIndexed { index, pointSet ->
            if (pointSet.objectId == relationStatus?.parentObjectId) {
                position = index
                set = pointSet
            }
        }
        pointSets.set(index = position, element = set.copy(totalLikes = observedSet?.totalLikes))
    }


}