package com.example.pinpointapp.domain.model

import java.util.Objects

data class Users(
    var id: String? = null,
    var ownerId: String? = null,
    var objectId: String? = null,
    var email: String? = null,
    var saved: List<PointSet>? = null,
    var pinned: List<PointSet>? = null,
    var liked: List<PointSet>? = null,
    var submitted: List<PointSet>? = null
)
