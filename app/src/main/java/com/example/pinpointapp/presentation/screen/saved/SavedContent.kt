package com.example.pinpointapp.presentation.screen.saved

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.presentation.components.DefaultContent

@Composable
fun SavedContent(
    navController: NavHostController,
    savedSet: List<PointSet>
) {
    if (savedSet.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Saved Sets",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    } else {
        DefaultContent(navController = navController, pointSets = savedSet)
    }
}