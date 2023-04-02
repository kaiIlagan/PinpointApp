package com.example.pinpointapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.keys.Keys.SELECTED_POINTS_KEY
import com.example.pinpointapp.navigation.Screen

@Composable
fun DefaultContent(
    navController: NavHostController,
    pointSets: List<PointSet>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(all = 6.dp)
    ) {
        items(
            items = pointSets,
            key = {
                it.objectId!!
            }
        ) {
            PointSetHolder(
                pointSet = it,
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = SELECTED_POINTS_KEY,
                        value = it
                    )
                    navController.navigate(Screen.Details.route)
                }
            )
        }
    }
}