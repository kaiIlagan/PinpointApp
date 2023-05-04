package com.example.pinpointapp.domain.model

import android.os.Parcelable
import com.backendless.persistence.Geometry
import com.backendless.persistence.LineString
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

// Data Class is a class that only contains a state and does not perform operation. The properties are defined in the constructor and getter/setters are automatically made. Properties are PUBLIC
@Parcelize // Passes the class in an intent between activities/screens
data class PointSet(
    var objectId: String? = null,
    var title: String? = null,
    var desc: String? = null,
    var approved: Boolean = false,
    var points: @RawValue Any? = Geometry.fromWKT("LINESTRING (-87.39646496 46.54275131, -87.40774155 46.56027672, -87.41354585 46.56463638)"), // Had to use RawValue as LineString was unable to be sent as a parcelized object since it is from Backendless and not native to Kotlin
    var totalLikes: Int? = null,
) : Parcelable
