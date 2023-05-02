package com.example.pinpointapp.presentation.screen.create

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Upload
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.backendless.persistence.LineString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.backendless.persistence.Geometry
import com.backendless.persistence.Point
import com.example.pinpointapp.R.string
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.ui.theme.topAppBarBackgroundColor
import com.example.pinpointapp.ui.theme.topAppBarContentColor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun CreateContent(onSubmitClicked: (String, String, LineString) -> Unit) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    val addedPoints = remember { mutableStateListOf<Point>() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Create Point Set",
            style = TextStyle(
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Description") }
        )
        Text(
            text = "Add Points",
            style = TextStyle(
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "(Press Finger Down On Map To Place Marker)",
            style = TextStyle(
                fontSize = MaterialTheme.typography.caption.fontSize,
                fontWeight = FontWeight.Normal
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(border = BorderStroke(2.dp, MaterialTheme.colors.topAppBarContentColor))
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                onMapClick = { coord ->
                    var point = Point()
                    point.x = coord.longitude
                    point.y = coord.latitude
                    addedPoints.add(point)
                },
                onMapLongClick = { coord ->
                    addedPoints.removeLastOrNull()
                }
            ) {
                addedPoints.forEach {
                    Marker(
                        MarkerState((LatLng(it.y, it.x))),
                        title = title,
                    )
                }
            }
        }
        Button(
            enabled = title.isNotEmpty() && desc.isNotEmpty() && addedPoints.isNotEmpty(),
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            onClick = {
                val setTitle = title
                val setDesc = desc
                var dummy = Point()
                dummy.x = 132.1
                dummy.y = 132.1
                addedPoints.add(dummy)
                val setPoints = LineString(addedPoints)
                onSubmitClicked(setTitle, setDesc, setPoints)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Point Icon",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = stringResource(id = string.create_submit), color = Color.White)
        }

    }
}

@Preview
@Composable
fun CreatePreview() {
    CreateContent(onSubmitClicked = { title, desc, points -> })
}
