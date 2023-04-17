package com.example.pinpointapp.domain.repository

import android.graphics.Point
import com.backendless.rt.data.RelationStatus
import com.example.pinpointapp.domain.model.PointSet
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getPointSets(): List<PointSet>
    suspend fun observeAddRelation(): Flow<RelationStatus?> // Observer for when a Point Set is liked and a User is "added" to that relation
    suspend fun observeDeleteRelation(): Flow<RelationStatus?> // Observer for when a Point Set is unliked and a User is "deleted" from that relation
    suspend fun observeApproval(): Flow<PointSet> // Observer for when a Point Set is approved that is created by the User // Observer for when a Point Set is approved that is created by the User
    suspend fun observeDeletedSets(): Flow<PointSet> // Observe for when a Point Set is deleted from the database
    suspend fun getLikeCount(objectId: String): PointSet
    suspend fun checkSavedSet(setObjectId: String, userObjectId: String): List<PointSet>
    suspend fun savePointSet(setObjectId: String, userObjectId: String): Int
    suspend fun unsavePointSet(setObjectId: String, userObjectId: String): Int
}