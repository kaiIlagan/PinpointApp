package com.example.pinpointapp.domain.repository

import com.example.pinpointapp.domain.model.PointSet

interface BackendlessDataSource {

    suspend fun getPointSets(): List<PointSet>
}