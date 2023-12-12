package com.cafealyzer.cafealyzer.ui.screen

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafealyzer.cafealyzer.R
import com.cafealyzer.cafealyzer.remote.response.FindCafeResult
import com.cafealyzer.cafealyzer.ui.activity.MainActivity
import com.cafealyzer.cafealyzer.ui.viewmodel.MapsViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(mapsViewModel: MapsViewModel = viewModel()) {
    val nearbyCafeData by mapsViewModel.nearbyCafeData.observeAsState()
    val isLoading by mapsViewModel.isLoading.observeAsState()

    val context = LocalContext.current
    val fusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }
    var lastKnownLocation by remember {
        mutableStateOf<android.location.Location?>(null)
    }
    var deviceLatLng by remember {
        mutableStateOf(mapsViewModel.deviceLatLng)
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
    }

    LaunchedEffect(Unit) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(context as MainActivity) { task ->
                if (task.isSuccessful) {
                    lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        deviceLatLng = LatLng(
                            lastKnownLocation!!.latitude, lastKnownLocation!!.longitude
                        )
                        mapsViewModel.deviceLatLng = deviceLatLng
                        mapsViewModel.latitude = deviceLatLng.latitude
                        mapsViewModel.longitude = deviceLatLng.longitude
                        cameraPositionState.position =
                            CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
                        Log.d(
                            TAG,
                            "Value LatLng: ${mapsViewModel.latitude}, ${mapsViewModel.longitude}"
                        )
                        mapsViewModel.fetchNearbyCafeData()
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
    }
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_FINE_LOCATION
        ),
        -> {
            Log.d(TAG, "Permission granted")
        }

        else -> {
            ActivityCompat.requestPermissions(
                context as MainActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        }
    }
    var cafeAnda by remember { mutableStateOf("") }
    var cafeKompetitor by remember { mutableStateOf("") }

    fun reverseCafe() {
        val temp = cafeAnda
        cafeAnda = cafeKompetitor
        cafeKompetitor = temp
    }

    if (isLoading == true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Red)
        }
    } else {
        nearbyCafeData?.let {
            Column {
                var searchText by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                    },
                    label = { Text("Cari Cafe") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            mapsViewModel.findCafe(searchText)
                        }
                    )
                )

                mapsViewModel.findCafeData.observeAsState().value?.let { cafeResults ->
                    if (cafeResults.isNotEmpty()) {
                        CafeSearchResults(cafeResults = cafeResults) { selectedCafe ->
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                LatLng(
                                    selectedCafe.findCafeGeometry.location.lat,
                                    selectedCafe.findCafeGeometry.location.lng
                                ),
                                18f
                            )
                        }
                    }
                }

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
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                ) {
                    nearbyCafeData?.forEach { cafe ->
                        CafeMarkers(
                            position = LatLng(
                                cafe.nearbyCafeGeometry.location.lat,
                                cafe.nearbyCafeGeometry.location.lng
                            ),
                            title = "${cafe.name} - ${cafe.rating}"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CafeSearchResults(cafeResults: List<FindCafeResult>, onCafeSelected: (FindCafeResult) -> Unit) {
    LazyColumn {
        items(cafeResults) { cafe ->
            Text(
                text = cafe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCafeSelected(cafe) }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun CafeMarkers(position: LatLng, title: String) {
    val markerstate = rememberMarkerState(null, position)
    Marker(
        state = markerstate,
        title = title,
    )
    markerstate.showInfoWindow()
}

private const val PERMISSION_REQUEST_CODE = 1001
private const val TAG = "MapsScreen"

