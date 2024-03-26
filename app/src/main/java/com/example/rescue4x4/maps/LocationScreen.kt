package com.example.rescue4x4.maps

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.rescue4x4.more.weather.Const
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

data class LocationList(
    val placeId: String,
    val name: String,
    val address: String
)

@SuppressLint("NotConstructor")
@Composable
fun LocationScreen(
    context: Context,
    currentLocation: LatLng,
    cameraPositionState: CameraPositionState,
    onUserLocationClick: () -> Unit
) {
    val uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
    var properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    var isSatelliteViewEnabled by remember { mutableStateOf(false) }
    var mapLoaded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("")}
    var searchResults by remember { mutableStateOf<List<LocationList>>(emptyList())}
    var isDropdownExpanded by remember { mutableStateOf(false) }


    // defining a launcher for requesting multiple permissions
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionMaps ->
        val areGranted = permissionMaps.values.all { it }
        if (areGranted) {
            LocationUtils.locationRequired = true
            LocationUtils.startLocationUpdates()
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
                    // check if all permissions are granted
                    if (Const.permissions.all {
                        // ContextCompat is used to check if the permission is granted
                            ContextCompat.checkSelfPermission(
                                context,
                                it
                            ) == PackageManager.PERMISSION_GRANTED
                        }) {
                            LocationUtils.startLocationUpdates()
                            mapLoaded = true
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                currentLocation, cameraPositionState.position.zoom)
                    } else {
                        // if not all permissions are granted, request for the permissions
                        launcherMultiplePermissions.launch(Const.permissions)
                    }
                }
            ) {
                  Marker(state = MarkerState(position = currentLocation))
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Search for places") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                isDropdownExpanded = true
                                searchResults = emptyList()
                                searchPlaces(
                                    searchQuery,
                                    currentLocation,
                                    context,
                                    onSearchResults = {
                                        searchResults = it
                                    })
                            }
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        searchResults.forEach { location ->
                            DropdownMenuItem(text = {
                                Text(text = "${location.name}, ${location.address}")
                            }, onClick = {
                                putMarkerOnMap(location.placeId, context, cameraPositionState)
                                isDropdownExpanded = false
                            })
                        }
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(5.dp)) {
                        FloatingActionButton(
                            onClick = { onUserLocationClick() },
                            modifier = Modifier
                                .size(60.dp)
                                .align(Alignment.Start)
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = "My Location",
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Switch(
                            checked = isSatelliteViewEnabled,
                            onCheckedChange = {
                                isSatelliteViewEnabled = it
                                properties =
                                    properties.copy(mapType = if (it) MapType.SATELLITE else MapType.NORMAL)
                            },
                            thumbContent = {
                                Icon(
                                    imageVector = if (isSatelliteViewEnabled) {
                                        Icons.Default.Check
                                    } else {
                                        Icons.Default.Close
                                    },
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.align(Alignment.End)
                        )
                        Text(
                            text = "Satellite Mode",
                            color = if (isSatelliteViewEnabled) Color.White else MaterialTheme.colorScheme.surface,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

fun putMarkerOnMap(
    placeId: String,
    context: Context,
    cameraPositionState: CameraPositionState,
) {
    // get place details using the provided placeId and PlacesClient
    getPlaceDetails(placeId, Places.createClient(context)) { latLng ->
        latLng?.let {
            // if the latlng is not null, create a new cameraposition
            val cameraPosition = CameraPosition.Builder()
                .target(it) // set the target to the latlng
                .zoom(17f) // set the zoom level
                .build()

            // update the camera position state with the new camera position
            cameraPositionState.position = cameraPosition
        }
    }
}

// function to get place details using the placeId
fun getPlaceDetails(placeId: String, placesClient: PlacesClient, onComplete: (LatLng?) -> Unit){
    // define the place fields to be retrieved(in this case, only latlng)
    val placeFields = listOf(Place.Field.LAT_LNG)
    // create a request to fetch place details using the placeId and placeFields
    val request = FetchPlaceRequest.newInstance(placeId, placeFields)

    // fetch the place details asynchronously
    placesClient.fetchPlace(request)
        .addOnSuccessListener { response ->
            // if the request is successful, extract the place information
            val place = response.place
            // get latlng of the place
            val latLng = place.latLng
            // call the onComplete lambda with the latlng
            onComplete(latLng)
        }
        .addOnFailureListener { exception ->
            if(exception is ApiException){
                Log.e("PlaceDetails", "Place details request failed: ${exception.message}")
            }
        }
}

fun searchPlaces(query: String, currentLocation: LatLng, context: Context, onSearchResults: (List<LocationList>) -> Unit) {
    // Initialize Places api client
    Places.initialize(context, "AIzaSyAG-a1-R5sgp7u6MG9gS2loziGDWlCgdNY") // api
    val placesClient = Places.createClient(context)

    // create a new token for autocomplete sessions
    val token = AutocompleteSessionToken.newInstance()

    //define search bounds on current location, it draws a square around the current location
    // and searches for places within that square
    val bounds = RectangularBounds.newInstance(
        LatLng(currentLocation.latitude - 0.15, currentLocation.longitude - 0.15),
        LatLng(currentLocation.latitude + 0.15, currentLocation.longitude + 0.15)
    )

    // creating a request for autocomplete predictions
    val request = FindAutocompletePredictionsRequest.builder()
        .setSessionToken(token)
        .setQuery(query)
        .setLocationBias(bounds)
        .build()

    // list for storing search results
    val locationList = mutableListOf<LocationList>()

    // perform the autocomplete search
    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            // iterate through the predictions and add them to the list
            for (prediction in response.autocompletePredictions) {
                val placeId = prediction.placeId
                val placeName = prediction.getPrimaryText(null).toString()
                val placeAddress = prediction.getSecondaryText(null).toString()

                Log.d(
                    "PlacePrediction",
                    "Place ID: $placeId, Name: $placeName, Address: $placeAddress"
                )
                locationList.add(LocationList(placeId, placeName, placeAddress))
            }
        }
        // once the search is complete, call the onSearchResults lambda
        .addOnCompleteListener {
            onSearchResults(locationList)
        }
        // handle any errors that may occur
        .addOnFailureListener { exception ->
            if (exception is ApiException) {
                Log.e("PlacePrediction", "Place prediction failed: ${exception.message}")
            }
        }
}
