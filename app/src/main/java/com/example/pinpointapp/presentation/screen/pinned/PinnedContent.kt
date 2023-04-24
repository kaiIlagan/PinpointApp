package com.example.pinpointapp.presentation.screen.pinned

import android.graphics.Paint.Align
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
import com.example.pinpointapp.util.RequestState

@Composable
fun PinnedContent(
    navController: NavHostController,
    pinnedSet: List<PointSet>,
    requestState: RequestState
) {
    when (requestState) {
        is RequestState.Success -> {
            if (pinnedSet.isEmpty()) {
                NoPinnedSets()
            } else {
                DefaultContent(navController = navController, pointSets = pinnedSet)
            }
        }
        is RequestState.Error -> {
            if (pinnedSet.isEmpty()) {
                NoPinnedSets()
            }
        }
        else -> {}
    }
}

@Composable
fun NoPinnedSets() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No Pinned Point Sets",
            style = TextStyle(
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}