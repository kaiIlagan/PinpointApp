package com.example.pinpointapp.data.repository

import android.util.Log
import com.backendless.Persistence
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.domain.repository.BackendlessDataSource
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BackendlessDataSourceImplementation @Inject constructor(
    private val backendless: Persistence
) : BackendlessDataSource {
    override suspend fun getPointSets(): List<PointSet> {
        val queryBuilder: DataQueryBuilder = DataQueryBuilder
            .create()
            .setProperties("Count(likes) as totalLikes", "points", "approved", "objectId", "title")
            .setWhereClause("approved = true")
            .setGroupBy("objectId")
        return suspendCoroutine { continuation ->
            backendless.of(PointSet::class.java)
                .find(queryBuilder, object : AsyncCallback<List<PointSet>> {
                    override fun handleResponse(response: List<PointSet>) {
                        Log.d("Get Point Sets", "Success")
                        continuation.resume(response)
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        Log.e("Get Point Sets", fault.toString())
                        continuation.resume(emptyList())
                    }

                })
        }
    }
}