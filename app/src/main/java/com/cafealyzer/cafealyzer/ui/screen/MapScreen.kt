package com.cafealyzer.cafealyzer.ui.screen

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafealyzer.cafealyzer.R
import com.cafealyzer.cafealyzer.ui.activity.MainActivity
import com.cafealyzer.cafealyzer.ui.component.mapscreen.SearchBar
import com.cafealyzer.cafealyzer.ui.viewmodel.MapsViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
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
    var cafeAnda by remember { mutableStateOf("") }
    var cafeKompetitor by remember { mutableStateOf("") }

    fun reverseCafe() {
        val temp = cafeAnda
        cafeAnda = cafeKompetitor
        cafeKompetitor = temp
    }

    Column {
        var searchText by remember { mutableStateOf("") }

        SearchBar(
            query = searchText,
            onQueryChange = {
                searchText = it
            },
            onSearchPerformed = {
                Log.d("Search", "Search performed with query: $searchText")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            TextField(
                value = cafeAnda,
                onValueChange = { cafeAnda = it },
                label = { Text("Cafe Anda") },
                modifier = Modifier
                    .weight(1f),
                readOnly = false
            )
            IconButton(
                onClick = {
                    cafeAnda = ""
                    cafeKompetitor = ""
                },
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            TextField(
                value = cafeKompetitor,
                onValueChange = { cafeKompetitor = it },
                label = { Text("Cafe Kompetitor") },
                modifier = Modifier
                    .weight(1f),
                readOnly = false
            )
            IconButton(
                onClick = { reverseCafe() },
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_reverse),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }
        GoogleMap(
            cameraPositionState = cameraPositionState,
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
}

private const val PERMISSION_REQUEST_CODE = 1001
private const val TAG = "MapsScreen"