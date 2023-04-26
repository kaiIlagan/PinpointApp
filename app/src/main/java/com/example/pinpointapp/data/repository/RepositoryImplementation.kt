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
        return backendlessDataSource.getLikeCount(objectId)
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

    override suspend fun checkPinnedSet(setObjectId: String, userObjectId: String): List<PointSet> {
        return backendlessDataSource.checkPinnedSet(setObjectId, userObjectId)
    }

    override suspend fun pinPointSet(setObjectId: String, userObjectId: String): Int {
        return backendlessDataSource.pinPointSet(setObjectId, userObjectId)
    }

    override suspend fun unpinPointSet(setObjectId: String, userObjectId: String): Int {
        return backendlessDataSource.unpinPointSet(setObjectId, userObjectId)
    }

    override suspend fun addLike(setObjectId: String, userObjectId: String): Int? {
        return backendlessDataSource.addLike(setObjectId, userObjectId)
    }

    override suspend fun removeLike(setObjectId: String, userObjectId: String): Int? {
        return backendlessDataSource.removeLike(setObjectId, userObjectId)
    }

    override suspend fun getSavedSets(userObjectId: String): List<PointSet> {
        return backendlessDataSource.getSavedSets(userObjectId)
    }

    override suspend fun observeSavedSets(userObjectId: String): Flow<RelationStatus?> {
        return backendlessDataSource.observeSavedSets(userObjectId)
    }

    override suspend fun getPinnedSets(userObjectId: String): List<PointSet> {
        return backendlessDataSource.getPinnedSets(userObjectId)
    }

    override suspend fun observePinnedSets(userObjectId: String): Flow<RelationStatus?> {
        return backendlessDataSource.observePinnedSets(userObjectId)
    }

    override suspend fun getSubmittedSets(userObjectId: String): List<PointSet> {
        return backendlessDataSource.getSubmittedSets(userObjectId)
    }

    override suspend fun observeSubmittedSets(userObjectId: String): Flow<PointSet> {
        return backendlessDataSource.observeSubmittedSets(userObjectId)
    }

    override suspend fun submitSet(pointSet: PointSet): PointSet {
        return backendlessDataSource.submitSet(pointSet)
    }
}