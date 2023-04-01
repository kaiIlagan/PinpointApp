package com.example.pinpointapp.domain.repository

import android.graphics.Point
import com.backendless.rt.data.RelationStatus
import com.example.pinpointapp.domain.model.PointSet
import kotlinx.coroutines.flow.Flow

interface BackendlessDataSource {
    suspend fun getPointSets(): List<PointSet> // Get Point Sets to populate Data Screen through DataViewModel, using data from
    suspend fun observeAddRelation(): Flow<RelationStatus?> // Observer for when a Point Set is liked and a User is "added" to that relation
    suspend fun observeDeleteRelation(): Flow<RelationStatus?> // Observer for when a Point Set is unliked and a User is "deleted" from that relation

    suspend fun observeApprovedSets(ownerId: String): Flow<PointSet> // Observer for when a Point Set is approved that is created by the User
    suspend fun observeNotApprovedSets(ownerId: String): Flow<PointSet> // Observer for when a Point Set is not approved that is created by the User
    suspend fun observeDeletedSets(ownerId: String): Flow<Point> // Observe for when a Point Set is deleted from the database
    suspend fun getLikeCount(objectId: String): PointSet
}