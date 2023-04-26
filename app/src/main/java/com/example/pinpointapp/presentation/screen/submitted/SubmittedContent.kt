package com.example.pinpointapp.presentation.screen.submitted

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.presentation.components.DefaultContent
import com.example.pinpointapp.util.RequestState
import com.example.pinpointapp.R.string

@Composable
fun SubmittedContent(
    navController: NavHostController,
    submittedSets: List<PointSet>,
    requestState: RequestState,
    onSubmitClicked: () -> Unit
) {
    if (requestState is RequestState.Success || requestState is RequestState.Error) {
        if (submittedSets.isEmpty()) {
            SubmitView(onSubmitClicked = onSubmitClicked)
        } else {
            DefaultContent(navController = navController, pointSets = submittedSets)
        }
    }
}

@Composable
fun SubmitView(onSubmitClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(120.dp)
                .padding(start = 5.dp)
                .fillMaxWidth(),
            imageVector = Icons.Default.CloudUpload,
            contentDescription = "PointSet Upload Image",
            tint = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = string.submit_points),
            fontSize = MaterialTheme.typography.h5.fontSize,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = string.submit_desc),
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            onClick = onSubmitClicked,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Icon(
                imageVector = Icons.Filled.PinDrop,
                contentDescription = "Point Icon",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = stringResource(id = string.submit_button), color = Color.White)
        }
    }
}

@Preview
@Composable
fun SubmitViewP() {
    SubmitView({ })
}
