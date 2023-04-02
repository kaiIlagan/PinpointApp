package com.example.pinpointapp.presentation.screen.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pinpointapp.domain.model.PointSet

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    navController: NavHostController,
    pointSet: PointSet
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DetailsTopBar(
                isSaved = false,
                isPinned = false,
                onBackClicked = { },
                onPinClicked = { },
                onSaveClicked = { }
            )
        },
        content = { DetailsContent(pointSet = pointSet) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 50.dp, 0.dp),
                backgroundColor = Color.Gray,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Heart Icon",
                        tint = Color.White
                    )
                },
                text = {
                    Text(
                        text = "${pointSet.totalLikes ?: "0"}",
                        color = Color.White
                    )
                },
                onClick = { })
        }
    )
}
