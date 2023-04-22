package com.example.pinpointapp.presentation.screen.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backendless.persistence.LineString
import com.backendless.persistence.Point
import com.backendless.rt.data.RelationStatus
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

    init { // Runs once when DataViewModel is initialized when the User navigates to DataScreen
        getPointSets()
        observeAddRelation()
        observeDeleteRelation()
        observeApproval()
        observeDeletedSets()
    }

    private fun getPointSets() {
        viewModelScope.launch(Dispatchers.IO) {
            pointSets.addAll(repository.getPointSets())
            pointSets.forEach {
                Log.d("DataViewModel", "${it.points.toString()}")
            }
        }
    }

    private fun observeAddRelation() { // Observes or "listens" to changes to our like column, when it is recieved the data is collected
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeAddRelation().collect { addRelation ->
                Log.d("HomeViewModel", "$addRelation")
                updateNumOfLikes(relationStatus = addRelation)
            }
        }
    }

    private fun observeDeleteRelation() { // Observes or "listens" to changes to our like column, when it is recieved the data is collected
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeDeleteRelation().collect { deleteRelation ->
                Log.d("HomeViewModel", "$deleteRelation")
                updateNumOfLikes(relationStatus = deleteRelation)
            }
        }
    }

    private fun observeApproval() { // Observes or "listens" to changes for a PointSet based on approval/disapproval. Has a great story of overcoming bugs and unpacking an "unknown" object
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeApproval().collect { set ->
                Log.d("observeApproval", set.points.toString())
                var map = set.points as HashMap<*, *>
                var points = mutableListOf<Point>()
                val coordinates = map["coordinates"] as Array<Any>
                coordinates.forEach {
                    val values = it as Array<Any>
                    val point = Point()
                    point.x = values[0] as Double
                    point.y = values[1] as Double
                    points.add(point)
                }
                set.points = LineString(points)
                if (set.approved) {
                    pointSets.add(set)
                } else {
                    pointSets.removeAll {
                        it.objectId == set.objectId
                    }
                }
            }
        }
    }

    private fun observeDeletedSets() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeDeletedSets().collect() { set ->
                pointSets.removeAll {
                    it.objectId == set.objectId
                }
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