package com.cafealyzer.cafealyzer.ui.screen

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cafealyzer.cafealyzer.R
import com.cafealyzer.cafealyzer.ui.activity.MainActivity
import com.cafealyzer.cafealyzer.ui.navigation.Screen
import com.cafealyzer.cafealyzer.ui.viewmodel.MapsViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapsScreen(mapsViewModel: MapsViewModel = viewModel(), navController: NavHostController) {
    val nearbyCafeData by mapsViewModel.nearbyCafeData.observeAsState()
    val selectedCafe by mapsViewModel.selectedCafe.observeAsState()
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
    val snackbarHostState = remember { SnackbarHostState() }

    val localCoroutineScope = rememberCoroutineScope()

    val cafeList by mapsViewModel.cafeList.observeAsState()

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

    fun reverseCafe() {
        mapsViewModel.reverseCafeList()
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

    if (isLoading == true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else {
        nearbyCafeData?.let {
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding)
                    ) {
                        OutlinedTextField(
                            value = cafeList?.getOrNull(0)?.title.orEmpty(),
                            onValueChange = { },
                            label = { Text("Cafe Anda") },
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 6.dp),
                            enabled = false,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                disabledLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                disabledTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            )
                        )
                        IconButton(
                            onClick = {
                                mapsViewModel.clearCafeList()
                            },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding)
                    ) {
                        OutlinedTextField(
                            value = cafeList?.getOrNull(1)?.title.orEmpty(),
                            onValueChange = { },
                            label = { Text("Cafe Kompetitor") },
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 6.dp),
                            enabled = false,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                disabledLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                disabledTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            )
                        )
                        IconButton(
                            onClick = { reverseCafe() },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_reverse),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                navController.navigate(Screen.Search.route) {
                                }
                            },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = "Cari Cafe"
                            )
                            Text("Cari Cafe", fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                if (cafeList?.size == 2) {
                                    val cafeName1 = cafeList?.getOrNull(0)?.title.orEmpty()
                                    val cafeName2 = cafeList?.getOrNull(1)?.title.orEmpty()
                                    navController.navigate("${Screen.CompareCafe.route}/$cafeName1/$cafeName2")
                                    mapsViewModel.getCafeDetail(cafeList!![0].placeId)
                                    mapsViewModel.getCafeDetail2(cafeList!![1].placeId)
                                } else {
                                    localCoroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Anda harus memilih 2 cafe untuk dibandingkan",
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text("Bandingkan", fontWeight = FontWeight.Bold)
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
                                title = "${cafe.name} - ${cafe.rating}",
                                placeId = cafe.placeId,
                                mapsViewModel = mapsViewModel
                            )
                        }
                        selectedCafe?.let { cafe ->
                            val cafeLatLng = LatLng(
                                cafe.findCafeGeometry.location.lat,
                                cafe.findCafeGeometry.location.lng
                            )
                            CafeMarkers(
                                position = cafeLatLng,
                                title = "${cafe.name} - ${cafe.rating}",
                                placeId = cafe.placeId,
                                mapsViewModel = mapsViewModel
                            )
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(
                                    LatLng(
                                        cafe.findCafeGeometry.location.lat,
                                        cafe.findCafeGeometry.location.lng
                                    ),
                                    18f
                                )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CafeMarkers(
    position: LatLng,
    title: String,
    placeId: String,
    mapsViewModel: MapsViewModel = viewModel(),
) {
    val markerState = rememberMarkerState(title, position)

    Marker(
        state = markerState,
        title = title,
        onInfoWindowClick = {
            Log.d(TAG, "PLACE ID ADALAH $placeId")

            mapsViewModel.addCafeToList(title, placeId)
        }
    )
    markerState.showInfoWindow()
}

private const val PERMISSION_REQUEST_CODE = 1001
private const val TAG = "MapsScreen"

