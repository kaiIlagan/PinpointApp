package com.example.pinpointapp.data.repository

import android.graphics.Point
import android.util.Log
import com.backendless.Persistence
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.backendless.rt.data.RelationStatus
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.domain.model.Users
import com.example.pinpointapp.domain.repository.BackendlessDataSource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class BackendlessDataSourceImplementation @Inject constructor(
    private val backendless: Persistence
) : BackendlessDataSource {
    override suspend fun getPointSets(): List<PointSet> {
        val queryBuilder: DataQueryBuilder = DataQueryBuilder
            .create()
            .setProperties(
                "Count(likes) as totalLikes",
                "points",
                "approved",
                "objectId",
                "desc",
                "title"
            )
            .setWhereClause("approved = true")
            .setGroupBy("objectId")
        return suspendCoroutine { continuation ->
            backendless.of(PointSet::class.java)
                .find(queryBuilder, object : AsyncCallback<List<PointSet>> {
                    override fun handleResponse(response: List<PointSet>) {
                        Log.d("GetPointSets", "$response")
                        continuation.resume(response)
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        Log.e("GetPointSets", fault.toString())
                        continuation.resume(emptyList())
                    }

                })
        }
    }

    //Notifies client applications when a like is added to a Point Set
    override suspend fun observeAddRelation(): Flow<RelationStatus?> {
        return callbackFlow {
            val event = backendless.of(PointSet::class.java).rt()
            val callback = object : AsyncCallback<RelationStatus> {
                override fun handleResponse(response: RelationStatus?) {
                    trySendBlocking(response)
                }

                override fun handleFault(fault: BackendlessFault?) {
                    fault?.message?.let { cancel(message = it) }
                }
            }
            event.addAddRelationListener("likes", callback)
            awaitClose {
                event.removeAddRelationListeners()
            }
        }
    }

    override suspend fun observeDeleteRelation(): Flow<RelationStatus?> {
        return callbackFlow {
            val event = backendless.of(PointSet::class.java).rt()
            val callback = object : AsyncCallback<RelationStatus> {
                override fun handleResponse(response: RelationStatus?) {
                    trySendBlocking(response)
                }

                override fun handleFault(fault: BackendlessFault?) {
                    fault?.message?.let { cancel(message = it) }
                }
            }
            event.addDeleteRelationListener("likes", callback)
            awaitClose {
                event.removeDeleteRelationListeners()
            }
        }
    }

    override suspend fun observeApproval(): Flow<PointSet> {
        return callbackFlow {
            Log.d("observeApproval", "here")
            val event = backendless.of(PointSet::class.java).rt()
            val callback = object : AsyncCallback<PointSet> {
                override fun handleResponse(response: PointSet) {
                    Log.d("observeApproval", response.toString())
                    trySendBlocking(response)
                }

                override fun handleFault(fault: BackendlessFault?) {
                    Log.d("observeApproval", fault.toString())
                    fault?.message?.let { cancel(message = it) }
                }
            }
            event.addUpdateListener("approved = false or approved = true", callback)
            awaitClose {
                event.removeUpdateListeners()
            }
        }
    }

    override suspend fun observeDeletedSets(): Flow<PointSet> {
        return callbackFlow {
            val event = backendless.of(PointSet::class.java).rt()
            val callback = object : AsyncCallback<PointSet> {
                override fun handleResponse(response: PointSet) {
                    trySendBlocking(response)
                }

                override fun handleFault(fault: BackendlessFault?) {
                    fault?.message?.let { cancel(message = it) }
                }
            }
            event.addDeleteListener(callback)
            awaitClose {
                event.removeDeleteListeners()
            }
        }
    }

    override suspend fun getLikeCount(objectId: String): PointSet {
        val queryBuilder: DataQueryBuilder = DataQueryBuilder
            .create()
            .setProperties("Count (likes) as totalLikes")

        return suspendCoroutine { continuation ->
            backendless.of(PointSet::class.java)
                .findById(objectId, queryBuilder, object : AsyncCallback<PointSet> {
                    override fun handleResponse(response: PointSet) {
                        continuation.resume(response)
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        continuation.resumeWithException(Exception(fault.message))
                    }

                })
        }
    }

    override suspend fun checkSavedSet(setObjectId: String, userObjectId: String): List<PointSet> {
        val query = DataQueryBuilder.create()
            .setWhereClause("Users[saved].object = '$userObjectId' and objectId = '$setObjectId'")

        return suspendCoroutine { continuation ->
            backendless.of(PointSet::class.java).find(
                query,
                object : AsyncCallback<List<PointSet>> {
                    override fun handleResponse(response: List<PointSet>) {
                        continuation.resume(response)
                    }

                    override fun handleFault(fault: BackendlessFault?) {
                        continuation.resume(emptyList())
                    }

                }
            )
        }
    }

    override suspend fun savePointSet(setObjectId: String, userObjectId: String): Int {
        return suspendCoroutine { continuation ->
            val user = Users(objectId = userObjectId)

            backendless.of(Users::class.java).addRelation(
                user,
                "saved",
                arrayListOf(PointSet(objectId = setObjectId)),
                object : AsyncCallback<Int> {
                    override fun handleResponse(response: Int) {
                        continuation.resume(response)
                    }

                    override fun handleFault(fault: BackendlessFault?) {
                        continuation.resumeWithException(Exception(fault?.message))
                    }

                }
            )
        }
    }

    override suspend fun unsavePointSet(setObjectId: String, userObjectId: String): Int {
        return suspendCoroutine { continuation ->
            val user = Users(objectId = userObjectId)

            backendless.of(Users::class.java).deleteRelation(
                user,
                "saved",
                arrayListOf(PointSet(objectId = setObjectId)),
                object : AsyncCallback<Int> {
                    override fun handleResponse(response: Int) {
                        continuation.resume(response)
                    }

                    override fun handleFault(fault: BackendlessFault?) {
                        continuation.resumeWithException(Exception(fault?.message))
                    }

                }
            )
        }
    }
}