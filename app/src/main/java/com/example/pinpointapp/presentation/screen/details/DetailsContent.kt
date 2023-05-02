package com.example.pinpointapp.presentation.screen.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.backendless.persistence.LineString
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.presentation.screen.getLineString
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun DetailsContent(
    pointSet: PointSet,
) {
    var linesString = remember(key1 = pointSet) {
        mutableStateOf(getLineString(pointSet))
    }

    val cameraPositionState = CameraPositionState(
        CameraPosition.fromLatLngZoom(
            LatLng(linesString!!.value.points[0].y, linesString!!.value.points[0].x),
            10f
        )
    )
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Log.d("lineString", linesString.toString())
        linesString!!.value.points.removeAt(linesString.value.points.lastIndex)
        Log.d("lineString remove last", linesString.toString())
        linesString!!.value.points.forEach {
            Log.d("lineString", "${it.x} , ${it.y}")
            Marker(MarkerState((LatLng(it.y, it.x))), title = pointSet.title)
        }
    }
}

