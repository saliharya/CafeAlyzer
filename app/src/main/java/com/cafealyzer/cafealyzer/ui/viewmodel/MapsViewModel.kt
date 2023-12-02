package com.cafealyzer.cafealyzer.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor() : ViewModel() {
    var deviceLatLng: LatLng = LatLng(0.0, 0.0)
    var latitude = deviceLatLng.latitude
    var longitude = deviceLatLng.longitude
}
