package com.example.pinpointapp.presentation.screen

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.LineString
import com.backendless.persistence.Point
import com.example.pinpointapp.domain.model.PointSet
import com.example.pinpointapp.keys.Keys.CLIENT_ID
import com.example.pinpointapp.keys.Keys.CLIENT_SECRET
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Code modeled after Stefan Jovanic from Udemy Course: Android & Web App Development using the Backendless Platform and modified for Senior Project use
//Linked here: https://www.udemy.com/course/android-web-app-development-using-the-backendless-platform/
// as well as Backendless documentation here: https://backendless.com/docs/android/
@Composable
fun StartActivityForResult(
    key: Any,
    onResultReceived: (String?) -> Unit,
    onDialogDismissed: (String) -> Unit,
    launcher: (ManagedActivityResultLauncher<Intent, ActivityResult>) -> Unit
) {
    val tag = "StartActivityForResult"
    val scope = rememberCoroutineScope()
    val activityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) { activityResult ->
        try {
            if (activityResult.resultCode == Activity.RESULT_OK) {
                val result = activityResult.data?.let { intent ->
                    Auth.GoogleSignInApi.getSignInResultFromIntent(intent)
                }
                val serverAuthCode = result?.signInAccount?.serverAuthCode
                Log.d(tag, "ACCESS TOKEN: $serverAuthCode")
                scope.launch(Dispatchers.IO) {
                    onResultReceived(getAccessToken(authCode = serverAuthCode))
                }
            } else {
                onDialogDismissed("Something went wrong with Result")
                Log.e(tag, "SOMETHING WENT WRONG ${activityResult.resultCode}")
            }
        } catch (e: Exception) {
            onDialogDismissed(e.message.toString())
            Log.e(tag, "${e.message}")
        }
    }

    LaunchedEffect(key1 = key) {
        launcher(activityLauncher)
    }
}

private fun getAccessToken(authCode: String?): String? {
    val tokenResponse: GoogleTokenResponse = try {
        GoogleAuthorizationCodeTokenRequest(
            NetHttpTransport(),
            GsonFactory(),
            "https://www.googleapis.com/oauth2/v4/token",
            CLIENT_ID,
            CLIENT_SECRET,
            authCode,
            "https://api.backendless.com/99C0754B-6B4E-417F-FF30-6281C4DFFB00/5BFB4C5E-104D-45E0-8705-1CBD1031D60B/users/oauth/googleplus/authorize"
        ).execute()
    } catch (e: Exception) {
        Log.d("getAccessToken", e.message.toString())
        return null
    }
    return tokenResponse.accessToken
}


fun signIn(
    activity: Activity,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestServerAuthCode(CLIENT_ID)
        .build()
    val client = GoogleSignIn.getClient(activity, gso)

    launcher.launch(client.signInIntent)
}

fun logout(onSuccess: () -> Unit, onFailed: () -> Unit) {
    Backendless.UserService.logout(
        object : AsyncCallback<Void> {
            override fun handleResponse(response: Void?) {
                Log.d("Logout", "Success")
                onSuccess()
            }

            override fun handleFault(fault: BackendlessFault?) {
                Log.e("Logout", fault?.message.toString())
                onFailed()
            }

        }
    )
}

fun getLineString(pointSet: PointSet): LineString {
    var parsed = pointSet.points as LineString
    val list = LineString(parsed.points.dropLast(1).toList() as List<Point>)
    return list
}

fun isConnectionError(message: String): String {
    return if (message.contains("No address associated with hostname")) {
        "No Internet Connection."
    } else {
        message
    }
}