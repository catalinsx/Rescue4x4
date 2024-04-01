package com.example.rescue4x4

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rescue4x4.maps.LocationScreen
import com.example.rescue4x4.maps.LocationUtils.fusedLocationClient
import com.example.rescue4x4.maps.LocationUtils.locationCallBack
import com.example.rescue4x4.maps.LocationUtils.locationRequired
import com.example.rescue4x4.maps.LocationUtils.startLocationUpdates
import com.example.rescue4x4.more.MoreScreen
import com.example.rescue4x4.more.askforhelp.AskForHelp
import com.example.rescue4x4.more.diagnosis.DTCScreen
import com.example.rescue4x4.more.weather.Fetcher.mainViewModel
import com.example.rescue4x4.more.weather.WeatherForm
import com.example.rescue4x4.more.weather.viewmodel.MainViewModel
import com.example.rescue4x4.sos.SOSScreen
import com.example.rescue4x4.ui.theme.Rescue4x4Theme
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState


/*
Context
prima si prima data un context e doar o instanta de clasa
val context: Context

Context in linii mari e doar un superclass care inglobeaza alte clase la randul ei
Object -> ContextWrapper -> ContextThemeWrapper -> Activity
						 -> Service
						 -> Application


- contextul poate fi asimilat ca un fel de bridge intre aplicatia mea si restul sistemului de operare,
 un middle man folosit ca exemplul urmator: E nevoie de context daca de exemplu folosesc un intent sa deschid youtubeul,
  contextul e middle manu intre deschdierea de youtube si aplicatia mea
- e necesar atunci cand aplicatia mea trebuie sa comunice cu celelalte componente ale Androidului
 */
class MainActivity : ComponentActivity() {

    private var isLocationInitialized by mutableStateOf(false)
    override fun onResume() {
        super.onResume()
        if (locationRequired) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationCallBack.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mainViewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
        setContent {
            var currentLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
            val cameraPositionState: CameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(currentLocation, 10f)
            }

            // defining the locationCallback to handle location updates
            // LocationCallBack class is a part of Android Location Services API

            locationCallBack = object: LocationCallback() {
                // overriding onLocationResult to handle location updates
                // it is called when new location updataes are available
                // p0 is just a variable name, used by convetion, it stands for parameter 0
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    // iterate through received locations
                    // each interation represents one location found by device
                    for (location in p0.locations) {
                        // update current location
                        currentLocation = LatLng(location.latitude, location.longitude)
                        if(!isLocationInitialized){
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLocation, 12f)
                            isLocationInitialized = true
                        }
                    }
                }
            }

            Rescue4x4Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Column {
                        NavHost(
                            navController = navController,
                            startDestination = "Map",
                            modifier = Modifier.weight(1f)
                        ) {
                            composable("Map") {
                                Log.d("composable", "Map")
                                LocationScreen(
                                    context = this@MainActivity,
                                    currentLocation = currentLocation,
                                    cameraPositionState = cameraPositionState,
                                    onUserLocationClick = {
                                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                            currentLocation,
                                            12f
                                        )
                                    }
                                )

                            }
                            composable("SOS") {
                                Log.d("composable", "SOS")
                                SOSScreen(currentLocation) }
                            composable("More") {
                                Log.d("composable", "More")
                                MoreScreen(navController)

                            }
                            composable("Weather") {
                                WeatherForm(context = this@MainActivity, currentLocation = currentLocation)
                            }
                            composable("Diagnosis") { DTCScreen() }
                            composable("AskForHelp") { AskForHelp(currentLocation, this@MainActivity) }
                        }
                        NavigationBarTest(navController = navController)
                    }
                }
            }
        }
    }
}
