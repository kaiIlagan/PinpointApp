package com.example.pinpointapp.domain.repository

import android.graphics.Point
import com.backendless.rt.data.RelationStatus
import com.example.pinpointapp.domain.model.PointSet
import kotlinx.coroutines.flow.Flow

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/

interface BackendlessDataSource { //Interface only contains Function Prototypes
    suspend fun getPointSets(): List<PointSet> // Get Point Sets to populate Data Screen through DataViewModel, using data from
    suspend fun observeAddRelation(): Flow<RelationStatus?> // Observer for when a Point Set is liked and a User is "added" to that relation
    suspend fun observeDeleteRelation(): Flow<RelationStatus?> // Observer for when a Point Set is unliked and a User is "deleted" from that relation
    suspend fun observeApproval(): Flow<PointSet> // Observer for when a Point Set is approved that is created by the User
    suspend fun observeDeletedSets(): Flow<PointSet> // Observe for when a Point Set is deleted from the database
    suspend fun getLikeCount(objectId: String): PointSet
    suspend fun checkSavedSet(setObjectId: String, userObjectId: String): List<PointSet>
    suspend fun savePointSet(setObjectId: String, userObjectId: String): Int
    suspend fun unsavePointSet(setObjectId: String, userObjectId: String): Int

    suspend fun checkPinnedSet(setObjectId: String, userObjectId: String): List<PointSet>

    suspend fun pinPointSet(setObjectId: String, userObjectId: String): Int

    suspend fun unpinPointSet(setObjectId: String, userObjectId: String): Int

    suspend fun addLike(setObjectId: String, userObjectId: String): Int?

    suspend fun removeLike(setObjectId: String, userObjectId: String): Int?
    suspend fun getSavedSets(userObjectId: String): List<PointSet>
    suspend fun observeSavedSets(userObjectId: String): Flow<RelationStatus?>

    suspend fun getPinnedSets(userObjectId: String): List<PointSet>
    suspend fun observePinnedSets(userObjectId: String): Flow<RelationStatus?>
    suspend fun getSubmittedSets(userObjectId: String): List<PointSet>
    suspend fun observeSubmittedSets(userObjectId: String): Flow<PointSet>
    suspend fun submitSet(pointSet: PointSet): PointSet
}