package com.example.pinpointapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.backendless.Backendless
import com.backendless.persistence.Geometry
import com.backendless.persistence.LineString
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.ui.theme.topAppBarContentColor

@Composable
fun PointSetHolder(
    pointSet: PointSet,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.topAppBarContentColor,
                shape = RoundedCornerShape(25.dp)
            )
            .clickable(enabled = pointSet.approved) {
                onClick()
            },
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 12.dp)
        ) {
            PointSet(pointSet = pointSet)
        }
        Surface(modifier = Modifier.align(Alignment.CenterEnd)) {
            NumberOfLikes(number = pointSet.totalLikes ?: 0)
        }
        if (!pointSet.approved) {
            WaitingForApproval()
        }
    }
}


@Composable
fun PointSet(pointSet: PointSet) {
    Column {
        var lineString: LineString? = pointSet.points as LineString?
        val numOfPoints = lineString!!.points.size
        pointSet.title?.let { Text(it) }
        pointSet.desc?.let { Text(it) }
        Text("$numOfPoints points")
    }
}

@Composable
fun NumberOfLikes(number: Int) {
    Surface(
        modifier = Modifier
            .padding(end = 12.dp),
        shape = RoundedCornerShape(size = 50.dp),
        color = Color.Black.copy(alpha = ContentAlpha.disabled)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(vertical = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Heart Icon",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "$number",
                color = Color.White
            )
        }
    }
}

@Composable
fun WaitingForApproval() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size = 20.dp))
            .background(Color.Black.copy(alpha = ContentAlpha.medium)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.HourglassBottom,
            contentDescription = "Hourglass Icon",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "Waiting for approval",
            color = Color.White
        )
    }
}

@Preview
@Composable
fun PointSetHolderPreview() {
    PointSetHolder(
        pointSet = PointSet(
            objectId = "0",
            "Test",
            "Desc",
            approved = true,
            LineString.fromWKT("LINESTRING (-87.39646496 46.54275131, -87.40774155 46.56027672, -87.41354585 46.56463638)"),
            1
        )
    ) {

    }
}