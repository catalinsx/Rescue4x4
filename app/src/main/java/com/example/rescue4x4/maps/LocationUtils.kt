package com.example.rescue4x4.maps

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority



object LocationUtils {
    // fusedlocationproviderclient for requesting  and managing location updates
    // this is like a helper tool that helps you get the location of the device, think of
    // a service that talks with gps and gives you the location of the device
    lateinit var fusedLocationClient: FusedLocationProviderClient

    // locationCallBack is used for handling location updates
    // this is like a phone line that the app keeps open to listen for location updates
    // when the location of the device changes, the fusedLocationClient sends the new location
    lateinit var locationCallBack: LocationCallback

    // indicates whether location updates are required
    // it is like a switch that tells the app wheters it needs to know the location or not
    var locationRequired: Boolean = false

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        locationCallBack.let {
            // creates a location request with high accuracy and 100ms interval
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build()

            // requests location updates from the fusedLocationClient
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                // this looper has the scope to request location updates on the main thread
                // it's basically something like " hey run the task on the main thread ", it is
                // important for updating the UI
                Looper.getMainLooper()
            )

        }
    }
}
