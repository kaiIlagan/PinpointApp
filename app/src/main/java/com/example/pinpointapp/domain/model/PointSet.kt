package com.example.pinpointapp.domain.model

import com.backendless.persistence.Geometry
import com.backendless.persistence.LineString

data class PointSet(
    var objectId: String? = null,
    var title: String? = null,
    var desc: String? = null,
    var approved: Boolean = false,
    var points: LineString = Geometry.fromWKT("LINESTRING (-87.39646496 46.54275131, -87.40774155 46.56027672, -87.41354585 46.56463638)"),
    var totalLikes: Int? = null,
)
