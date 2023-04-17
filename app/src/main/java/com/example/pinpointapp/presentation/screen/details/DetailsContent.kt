package com.example.pinpointapp.presentation.screen.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.backendless.persistence.LineString
import com.example.pinpointapp.domain.model.PointSet
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DetailsContent(
    pointSet: PointSet,
) {
    var linesString: LineString = pointSet!!.points as LineString
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(linesString.points[0].y, linesString.points[0].x),
            10f
        )
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Log.d("lineString", linesString.toString())
        linesString.points.forEach {
            Log.d("lineString", "${it.x} , ${it.y}")
            Marker(MarkerState((LatLng(it.y, it.x))), title = pointSet.title)
        }
    }
}

