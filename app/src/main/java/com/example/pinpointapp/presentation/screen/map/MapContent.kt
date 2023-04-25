package com.example.pinpointapp.presentation.screen.map

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.presentation.screen.getLineString
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.random.Random

@Composable
fun MapContent(
    mapSets: List<PointSet>
) {
    val cameraPositionState = rememberCameraPositionState {
        if (mapSets.isNotEmpty()) {
            position = CameraPosition.fromLatLngZoom(
                LatLng(
                    getLineString(mapSets[0]).points[0].y,
                    getLineString(mapSets[0]).points[0].x
                ), 10f
            )
        } else {
            Log.d("MapContent", "EMPTY")
        }
    }


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        mapSets.forEach { pointSet ->
            var randColor = Random.nextInt(0, 360).toFloat()
            var lineString = getLineString(pointSet)
            lineString!!.points.forEach {
                Marker(
                    MarkerState((LatLng(it.y, it.x))),
                    title = pointSet.title,
                    icon = BitmapDescriptorFactory.defaultMarker(randColor)
                )
            }
        }
    }
}