package com.example.pinpointapp.data.repository

import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.domain.repository.BackendlessDataSource
import com.example.pinpointapp.domain.repository.Repository
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val backendless: BackendlessDataSource
) : Repository {
    override suspend fun getPointSets(): List<PointSet> {
        return backendless.getPointSets()
    }
}