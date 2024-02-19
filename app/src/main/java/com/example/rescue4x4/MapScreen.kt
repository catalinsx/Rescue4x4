package com.example.rescue4x4

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(location: LocationData) {
    val userLocation = remember { mutableStateOf(LatLng(location.latitude, location.longitude)) }
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation.value, 10f)
    }

    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }

    var isSatelliteViewEnabled by remember { mutableStateOf(false) }

    Column {
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                properties = properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState
            )

            Switch(
                modifier = Modifier.align(Alignment.TopStart),
                checked = isSatelliteViewEnabled,
                onCheckedChange = {
                    isSatelliteViewEnabled = it
                    properties = properties.copy(mapType = if (it) MapType.SATELLITE else MapType.NORMAL)
                }
            )
        }
    }
}



