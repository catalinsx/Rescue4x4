package com.example.rescue4x4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.currentCameraPositionState
    @Composable
    fun SOSScreen(currentLocation: LatLng){
       // val currentLocation = (LocalContext.current as MainActivity)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ){
            Text(text = "Location: ${currentLocation.longitude} / ${currentLocation.latitude}")
        }
    }

