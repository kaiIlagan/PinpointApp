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

    override suspend fun observeApproval(): Flow<PointSet> {
        return backendlessDataSource.observeApproval()
    }

    override suspend fun observeDeletedSets(): Flow<PointSet> {
        return backendlessDataSource.observeDeletedSets()
    }

    override suspend fun getLikeCount(objectId: String): PointSet {
        return backendlessDataSource.getLikeCount(objectId = objectId)
    }

    override suspend fun checkSavedSet(setObjectId: String, userObjectId: String): List<PointSet> {
        return backendlessDataSource.checkSavedSet(setObjectId, userObjectId)
    }

    override suspend fun savePointSet(setObjectId: String, userObjectId: String): Int {
        return backendlessDataSource.savePointSet(setObjectId, userObjectId)
    }

    override suspend fun unsavePointSet(setObjectId: String, userObjectId: String): Int {
        return backendlessDataSource.unsavePointSet(setObjectId, userObjectId)
    }
}