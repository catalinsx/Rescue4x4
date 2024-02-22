package com.example.rescue4x4.maps

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
/*
open class Location {
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationCallBack: LocationCallback
    var locationRequired: Boolean = false

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        locationCallBack.let {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 100
            )
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(3000)
                .setMaxUpdateDelayMillis(100)
                .build()

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )

        }
    }


} */