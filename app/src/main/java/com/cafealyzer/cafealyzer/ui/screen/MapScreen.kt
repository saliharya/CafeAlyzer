package com.cafealyzer.cafealyzer.ui.screen

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafealyzer.cafealyzer.ui.activity.MainActivity
import com.cafealyzer.cafealyzer.ui.viewmodel.MapsViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapsScreen() {
    val context = LocalContext.current
    val fusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }
    var lastKnownLocation by remember {
        mutableStateOf<android.location.Location?>(null)
    }
    val viewModel: MapsViewModel = viewModel()
    var deviceLatLng by remember {
        mutableStateOf(viewModel.deviceLatLng)
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
    }

    if (ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(context as MainActivity) { task ->
                if (task.isSuccessful) {
                    lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        deviceLatLng = LatLng(
                            lastKnownLocation!!.latitude, lastKnownLocation!!.longitude
                        )
                        viewModel.deviceLatLng = deviceLatLng
                        viewModel.latitude = deviceLatLng.latitude
                        viewModel.longitude = deviceLatLng.longitude
                        cameraPositionState.position =
                            CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
                        Log.d(TAG, "Value LatLng: ${viewModel.latitude}, ${viewModel.longitude}")
                    } else {
                        Log.d(TAG, "Last known location is null. Using defaults.")
                    }
                } else {
                    Log.d(TAG, "Failed to get last known location.")
                    Log.e(TAG, "Exception: %s", task.exception)
                }
            }
        } catch (securityException: SecurityException) {
            Log.e(TAG, "SecurityException: ${securityException.message}")
        }
    } else {
        ActivityCompat.requestPermissions(
            context as MainActivity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE
        )
    }

    GoogleMap(
        cameraPositionState = cameraPositionState
    ) {
        MarkerInfoWindowContent(
            state = MarkerState(
                position = deviceLatLng
            )
        ) { marker ->
            Text(marker.title ?: "You", color = Color.Red)
        }
    }
}

private const val PERMISSION_REQUEST_CODE = 1001
private const val TAG = "MapsScreen"