package com.example.pinpointapp.domain.model

import com.backendless.persistence.LineString

data class PointSet(
    var objectId: String? = null,
    var title: String? = null,
    var desc: String? = null,
    var approved: Boolean = false,
    var points: LineString? = null,
    var totalLikes: Int? = null,
)
