package com.example.pinpointapp.data.repository

import android.graphics.Point
import com.backendless.rt.data.RelationStatus
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.domain.repository.BackendlessDataSource
import com.example.pinpointapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val backendlessDataSource: BackendlessDataSource
) : Repository {
    override suspend fun getPointSets(): List<PointSet> {
        return backendlessDataSource.getPointSets()
    }

    override suspend fun observeAddRelation(): Flow<RelationStatus?> {
        return backendlessDataSource.observeAddRelation()
    }

    override suspend fun observeDeleteRelation(): Flow<RelationStatus?> {
        return backendlessDataSource.observeDeleteRelation()
    }

    override suspend fun observeApprovedSets(ownerId: String): Flow<PointSet> {
        TODO("Not yet implemented")
    }

    override suspend fun observeNotApprovedSets(ownerId: String): Flow<PointSet> {
        TODO("Not yet implemented")
    }

    override suspend fun observeDeletedSets(ownerId: String): Flow<Point> {
        TODO("Not yet implemented")
    }

    override suspend fun getLikeCount(objectId: String): PointSet {
        return backendlessDataSource.getLikeCount(objectId = objectId)
    }
}