package com.example.rescue4x4.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Switch
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

/*

class MapsCompose : Location(){
    @Composable
    fun MapScreen() {
        val context = LocalContext.current
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        var currentLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
        val cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
        }
        val cameraPositionState by remember { mutableStateOf(cameraPosition) }


        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                for (location in p0.locations) {
                    currentLocation = LatLng(location.latitude, location.longitude)

                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                        currentLocation, cameraPositionState.position.zoom
                    )
                }
            }
        }
    }
}

@Composable
fun LocationScreen(
    context: Context,
    currentLocation: LatLng,
    cameraPositionState: CameraPositionState
) {

    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
    var properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    var isSatelliteViewEnabled by remember { mutableStateOf(false) }
    var mapLoaded by remember { mutableStateOf(false) }

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionMaps ->
        val areGranted = permissionMaps.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            locationRequired = true
            startLocationUpdates()
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column {
        if (!mapLoaded) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                properties = properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                    if (permissions.all {
                            ContextCompat.checkSelfPermission(
                                context,
                                it
                            ) == PackageManager.PERMISSION_GRANTED
                        }) {
                        startLocationUpdates()
                        mapLoaded = true
                    } else {
                        launcherMultiplePermissions.launch(permissions)
                    }

                }
            ) {
                Marker(
                    state = MarkerState(position = currentLocation)
                )
            }
            Column {
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
                    text = "Satellite Mode",
                    color = if (isSatelliteViewEnabled) Color.White else MaterialTheme.colorScheme.surface,
                    fontStyle = FontStyle.Italic
                )
                /*  Switch(
                      modifier = Modifier
                          .padding(start = 8.dp, top = 0.dp),
                      checked = isSatelliteViewEnabled,
                      onCheckedChange = {
                          isSatelliteViewEnabled = it
                          properties =
                              properties.copy(mapType = if (it) MapType.SATELLITE else MapType.NORMAL)
                      },
                      thumbContent = {
                          Icon(
                              imageVector = if(isSatelliteViewEnabled){
                                  Icons.Default.Check
                              }else{
                                  Icons.Default.Close
                              },
                              contentDescription = null
                          )
                      }
                  )*/
            }
        }
    }
} */