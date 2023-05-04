package com.example.pinpointapp.presentation.screen.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pinpointapp.R
import com.example.pinpointapp.domain.model.MessageBarState
import com.example.pinpointapp.presentation.components.GoogleLoginButton
import com.example.pinpointapp.presentation.components.MessageBar

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/
@Composable
fun LoginContent(
    signedInState: Boolean,
    messageBarState: MessageBarState?,
    onButtonClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.weight(1f)) {
            MessageBar(messageBarState = messageBarState)
        }
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .size(120.dp)
                    .offset(y = -40.dp),
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account Circle Image",
                tint = MaterialTheme.colors.onBackground
            )
            Spacer(
                modifier = Modifier
                    .height(2.dp)
            )
            Text(
                text = stringResource(id = R.string.login_header),
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.h5.fontSize
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .alpha(ContentAlpha.medium),
                text = stringResource(id = R.string.login_subtitle),
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(40.dp))
            GoogleLoginButton(
                loadingState = signedInState,
                onClick = onButtonClicked
            )
        }
    }
}