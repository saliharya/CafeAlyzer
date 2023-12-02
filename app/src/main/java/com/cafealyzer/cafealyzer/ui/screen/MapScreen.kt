@file:OptIn(ExperimentalMaterial3Api::class)

package com.cafealyzer.cafealyzer.ui.screen

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cafealyzer.cafealyzer.ui.activity.MainActivity
import com.cafealyzer.cafealyzer.ui.component.mapscreen.CustomSearchBar
import com.cafealyzer.cafealyzer.ui.viewmodel.MapsViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen() {
    val context = LocalContext.current
    val fusedLocationProviderClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }
    var lastKnownLocation by remember {
        mutableStateOf<Location?>(null)
    }
    val viewModel: MapsViewModel = viewModel()
    var deviceLatLng by remember {
        mutableStateOf(viewModel.deviceLatLng)
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
    }
    val locationResult = fusedLocationProviderClient.lastLocation
    locationResult.addOnCompleteListener(context as MainActivity) { task ->
        if (task.isSuccessful) {
            lastKnownLocation = task.result
            if (lastKnownLocation != null) {
                deviceLatLng = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                viewModel.deviceLatLng = deviceLatLng
                viewModel.latitude = deviceLatLng.latitude
                viewModel.longitude = deviceLatLng.longitude
                cameraPositionState.position = CameraPosition.fromLatLngZoom(deviceLatLng, 18f)
                Log.d(TAG, "Value LatLng: ${viewModel.latitude}, ${viewModel.longitude}")
            } else {
                Log.d(TAG, "Last known location is null. Using defaults.")
            }
        } else {
            Log.d(TAG, "Failed to get last known location.")
            Log.e(TAG, "Exception: %s", task.exception)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Pilih Cafe Anda dan Kompetitor")
                }
            )
        },
        content = { innerpadding ->
            Column {
                var searchText by remember { mutableStateOf("") }

                CustomSearchBar(
                    query = searchText,
                    onQueryChange = {
                        searchText = it
                    },
                    onSearchPerformed = {
                        Log.d("Search", "Search performed with query: $searchText")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerpadding)
                )

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
    )
}